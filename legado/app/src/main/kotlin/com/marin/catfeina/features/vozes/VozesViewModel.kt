// ===================================================================================
// Arquivo: com.marin.catfeina.features.vozes.VozesViewModel.kt
//
// Descrição: ViewModel para a tela de Configurações.
//
// Propósito: Este ViewModel gerencia o estado e a lógica de negócios para as configurações do
// aplicativo, como a seleção de voz do Text-to-Speech (TTS). Ele se comunica com
// a camada de serviço (CatfeinaTtsService) e a camada de dados (UserPreferencesRepository)
// para fornecer os dados à UI e para salvar as escolhas do usuário.
// ===================================================================================
package com.marin.catfeina.features.vozes

import android.speech.tts.Voice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.UserPreferencesRepository
import com.marin.catfeina.core.tts.CatfeinaTtsService
import com.marin.catfeina.core.tts.TtsStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Representa o estado da UI para a tela de configurações de TTS.
 *
 * @param availableVoices A lista de vozes formatadas como [VoiceInfo].
 * @param selectedVoiceId O ID da voz atualmente selecionada pelo usuário. Pode ser nulo se for a padrão.
 */
data class TtsSettingsUiState(
    val availableVoices: List<VoiceInfo> = emptyList(),
    val selectedVoiceId: String? = null
)

@HiltViewModel
class VozesViewModel @Inject constructor(
    private val ttsService: CatfeinaTtsService,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TtsSettingsUiState())
    val uiState = _uiState.asStateFlow()

    private val _amostraEmReproducaoId = MutableStateFlow<String?>(null)
    val amostraEmReproducaoId = _amostraEmReproducaoId.asStateFlow()

    init {
        loadTtsSettings()

        // Observa o status geral do TTS para limpar o estado da amostra.
        viewModelScope.launch {
            ttsService.ttsStatus
                .filter { it == TtsStatus.STOPPED } // Corrigido: STOPPED é o estado de prontidão.
                .distinctUntilChanged()
                .collectLatest {
                    if (_amostraEmReproducaoId.value != null) {
                        _amostraEmReproducaoId.value = null
                    }
                }
        }
    }

    private fun loadTtsSettings() {
        viewModelScope.launch {
            // Combina o status do TTS e a preferência de voz salva.
            combine(
                ttsService.ttsStatus,
                userPreferencesRepository.selectedTtsVoiceId
            ) { status, selectedId ->
                val voiceInfoList = if (status == TtsStatus.STOPPED) { // Corrigido: STOPPED indica que o serviço está pronto.
                    // Transforma a lista de 'Voice' em uma lista de 'VoiceInfo'
                    ttsService.getAvailableVoices().map { voice ->
                        mapToVoiceInfo(voice)
                    }
                } else {
                    emptyList()
                }

                TtsSettingsUiState(
                    availableVoices = voiceInfoList,
                    selectedVoiceId = selectedId?.ifBlank { null } // Garante que string vazia seja tratada como nula
                )
            }.collectLatest { newState ->
                _uiState.value = newState
            }
        }
    }

    /**
     * Toca uma amostra de texto usando a voz especificada (ou a padrão).
     */
    fun playSample(text: String, voice: Voice? = null) {
        if (_amostraEmReproducaoId.value != null) return // Evita cliques múltiplos

        val voiceId = voice?.name ?: "system_default"
        _amostraEmReproducaoId.value = voiceId
        // Corrigido: Usa 'falar' e remove o callback, pois a lógica agora é reativa.
        ttsService.falar(text)
    }

    /**
     * Chamado pela UI quando o usuário seleciona uma nova voz na lista.
     * Salva a nova preferência no repositório.
     *
     * @param voice O objeto Voice que foi selecionado. Se for null, salva uma string vazia para representar a seleção padrão.
     */
    fun selectVoice(voice: Voice?) {
        viewModelScope.launch {
            userPreferencesRepository.setSelectedTtsVoiceId(voice?.name ?: "")
        }
    }

    /**
     * Função auxiliar para mapear um objeto 'Voice' para nosso 'VoiceInfo'.
     */
    private fun mapToVoiceInfo(voice: Voice): VoiceInfo {
        val nome = formatVoiceName(voice)
        val idioma = "${voice.locale.displayLanguage} (${voice.locale.country})"
        val qualidade = when {
            voice.isNetworkConnectionRequired -> "Rede"
            else -> "Local"
        }
        val genero = inferirGenero(voice)

        return VoiceInfo(
            id = voice.name,
            nome = nome,
            idioma = idioma,
            qualidade = qualidade,
            genero = genero,
            voice = voice
        )
    }

    /**
     * Formata o nome de uma voz para ser mais legível.
     */
    private fun formatVoiceName(voice: Voice): String {
        val name = voice.name.lowercase()

        if (name.startsWith("pt-br-x-")) {
            val parts = name.split('-')
            if (parts.size >= 4) {
                val voiceType = parts[3].uppercase()
                val quality = when(parts.last()) {
                    "network" -> "Rede"
                    "local" -> "Local"
                    else -> parts.last().replaceFirstChar { it.titlecase() }
                }
                return "Voz $voiceType ($quality)"
            }
        }
        if (name.contains("language")) {
            return "Voz do Idioma Padrão"
        }
        return voice.name
    }

    /**
     * Tenta inferir o gênero da voz baseado no seu nome.
     */
    private fun inferirGenero(voice: Voice): String {
        val name = voice.name.lowercase()
        // Heurística simples baseada nos nomes comuns dos motores de voz do Google
        return when {
            name.contains("afs") -> "Feminina" // 'afs' é frequentemente uma voz feminina
            name.contains("ptd") -> "Feminina" // 'ptd' também
            name.contains("pte") -> "Masculina"// 'pte' é frequentemente uma voz masculina
            else -> "Não especificado"
        }
    }
}
