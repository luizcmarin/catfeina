// ===================================================================================
// Arquivo: com.marin.catfeina.features.vozes.VozesScreen.kt
// Descrição: Tela de Composable para as configurações do aplicativo.
//
// Propósito:// Esta tela permite ao usuário configurar a seleção da voz (locutor) para o
// serviço de Text-to-Speech (TTS). A tela agora exibe detalhes ricos sobre cada
// voz e permite ao usuário ouvir uma amostra antes de selecionar.
// ===================================================================================

package com.marin.catfeina.features.vozes

import android.speech.tts.Voice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.rive.RiveLoadingIndicator

/**
 * Data class fornecida para armazenar informações detalhadas sobre cada voz.
 */
data class VoiceInfo(
    val id: String,
    val nome: String,
    val idioma: String,
    val qualidade: String,
    val genero: String,
    val voice: Voice
)

// =================================================================================
// TELA PRINCIPAL E SEUS COMPONENTES
// =================================================================================

@Composable
fun VozesScreen(
    viewModel: VozesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val amostraEmReproducaoId by viewModel.amostraEmReproducaoId.collectAsState()

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues
        ) {
            item {
                Text(
                    text = "Seleção de Voz (TTS)", // Título da seção
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (uiState.availableVoices.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        RiveLoadingIndicator(modifier = Modifier.size(100.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Carregando vozes...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                // Opção para usar a voz padrão do sistema
                item {
                    VoiceSelectionCard(
                        voiceName = "Voz Padrão do Sistema",
                        isSelected = uiState.selectedVoiceId == null,
                        isSamplePlaying = amostraEmReproducaoId == "system_default",
                        isAnySamplePlaying = amostraEmReproducaoId != null,
                        onSelected = { viewModel.selectVoice(null) },
                        onPlaySample = { viewModel.playSample("Usando a voz padrão do sistema.") }
                    )
                }

                // Lista de vozes específicas
                items(uiState.availableVoices, key = { it.id }) { voiceInfo ->
                    VoiceSelectionCard(
                        voiceName = voiceInfo.nome,
                        isSelected = uiState.selectedVoiceId == voiceInfo.id,
                        isSamplePlaying = amostraEmReproducaoId == voiceInfo.id,
                        isAnySamplePlaying = amostraEmReproducaoId != null,
                        onSelected = { viewModel.selectVoice(voiceInfo.voice) },
                        onPlaySample = { viewModel.playSample("Olá, eu sou a ${voiceInfo.nome}.", voiceInfo.voice) },
                        voiceInfo = voiceInfo
                    )
                }
            }
        }
    }
}


@Composable
private fun VoiceSelectionCard(
    voiceName: String,
    isSelected: Boolean,
    isSamplePlaying: Boolean,
    isAnySamplePlaying: Boolean,
    onSelected: () -> Unit,
    onPlaySample: () -> Unit,
    voiceInfo: VoiceInfo? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onSelected),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Gênero e Nome da Voz
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (voiceInfo != null) {
                        IconeGenero(genero = voiceInfo.genero)
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                    }
                    Text(
                        text = voiceName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
                RadioButton(
                    selected = isSelected,
                    onClick = onSelected
                )
            }

            // Exibe detalhes adicionais se disponíveis
            if (voiceInfo != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Item de Qualidade com Ícone
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconeQualidade(qualidade = voiceInfo.qualidade)
                        Spacer(Modifier.padding(start = 6.dp))
                        Text(
                            text = voiceInfo.qualidade,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Item de Idioma
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icones.Idioma,
                            contentDescription = "Idioma da voz",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.padding(start = 6.dp))
                        Text(
                            text = voiceInfo.idioma,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onPlaySample,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isAnySamplePlaying // Desabilita se qualquer amostra estiver tocando
            ) {
                if (isSamplePlaying) {
                    // <<<<<<< ALTERAÇÃO AQUI >>>>>>>
                    RiveLoadingIndicator(modifier = Modifier.size(40.dp))
                } else {
                    Text("Ouvir amostra")
                }
            }
        }
    }
}

@Composable
fun IconeGenero(genero: String) {
    val emoji = when (genero.lowercase()) {
        "feminina" -> "👩‍🦰"
        "masculina" -> "👨‍🦱"
        else -> "👤" // Um ícone neutro/padrão
    }
    Text(text = emoji, fontSize = 24.sp)
}

/** Exibe um ícone representando a qualidade da voz (Rede ou Local).
 */
@Composable
fun IconeQualidade(qualidade: String) {
    val (icon, description) = when (qualidade.lowercase()) {
        "rede" -> Icones.QualidadeVozBoa to "Voz de alta qualidade (rede)"
        "local" -> Icones.QualidadeVozPadrao to "Voz padrão (local)"
        else -> Icones.QualidadeVozDesconhecida to "Qualidade desconhecida"
    }
    Icon(imageVector = icon, contentDescription = description)
}
