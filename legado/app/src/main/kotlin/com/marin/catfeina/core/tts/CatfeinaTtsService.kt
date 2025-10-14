/*
 * Arquivo: com.marin.catfeina.core.tts.CatfeinaTtsService.kt
 * Descrição: Serviço para gerenciar o Text-to-Speech (TTS) no aplicativo.
 */
package com.marin.catfeina.core.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import com.marin.catfeina.core.data.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatfeinaTtsService @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val userPreferencesRepository: UserPreferencesRepository
) : TextToSpeech.OnInitListener {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var tts: TextToSpeech? = null
    private val ptBrLocale = Locale.forLanguageTag("pt-BR")

    private var textoCompletoAtual: String? = null
    private var proximoIndiceParaFalar: Int = 0
    private var ultimoIndiceProgresso: Int = 0

    private val _status = MutableStateFlow(TtsStatus.UNINITIALIZED)
    val ttsStatus: StateFlow<TtsStatus> = _status.asStateFlow()

    init {
        _status.update { TtsStatus.INITIALIZING }
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = ptBrLocale
            setupProgressListener()
            observeAndApplySelectedVoice()
            _status.update { TtsStatus.STOPPED }
        } else {
            _status.update { TtsStatus.ERROR }
        }
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                serviceScope.launch {
                    resetarParaFinalizado()
                }
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                _status.update { TtsStatus.ERROR }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                onError(utteranceId, -1)
            }

            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                ultimoIndiceProgresso = proximoIndiceParaFalar + start
            }
        })
    }

    private fun observeAndApplySelectedVoice() {
        serviceScope.launch {
            val initialVoiceId = userPreferencesRepository.selectedTtsVoiceId.first()
            applyVoice(initialVoiceId)
            userPreferencesRepository.selectedTtsVoiceId.collect { voiceId ->
                applyVoice(voiceId)
            }
        }
    }

    private fun applyVoice(voiceId: String?) {
        val ttsEngine = tts ?: return
        val voiceObject = ttsEngine.voices.firstOrNull { it.name == voiceId }
        ttsEngine.voice = voiceObject ?: ttsEngine.defaultVoice
    }

    fun getAvailableVoices(): List<Voice> {
        return tts?.voices?.filter { it.locale == ptBrLocale } ?: emptyList()
    }

    fun falar(texto: String) {
        val ttsEngine = tts ?: return
        if (_status.value == TtsStatus.INITIALIZING || _status.value == TtsStatus.ERROR || texto.isBlank()) return

        textoCompletoAtual = texto
        proximoIndiceParaFalar = 0
        ultimoIndiceProgresso = 0
        _status.update { TtsStatus.SPEAKING }
        ttsEngine.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "utterance_principal")
    }

    fun pausar() {
        if (_status.value == TtsStatus.SPEAKING) {
            proximoIndiceParaFalar = ultimoIndiceProgresso
            tts?.stop()
            _status.update { TtsStatus.PAUSED }
        }
    }

    fun continuar() {
        val ttsEngine = tts ?: return
        val texto = textoCompletoAtual ?: return

        _status.update { TtsStatus.SPEAKING }
        val textoRestante = texto.substring(proximoIndiceParaFalar.coerceIn(0, texto.length))
        ttsEngine.speak(textoRestante, TextToSpeech.QUEUE_FLUSH, null, "utterance_principal")
    }

    fun parar() {
        if (_status.value == TtsStatus.SPEAKING || _status.value == TtsStatus.PAUSED) {
            tts?.stop()
            resetarParaFinalizado()
        }
    }

    private fun resetarParaFinalizado() {
        textoCompletoAtual = null
        proximoIndiceParaFalar = 0
        ultimoIndiceProgresso = 0
        _status.update { TtsStatus.STOPPED }
    }

    fun desligar() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        _status.update { TtsStatus.UNINITIALIZED }
    }
}

enum class TtsStatus {
    UNINITIALIZED, INITIALIZING, STOPPED, SPEAKING, PAUSED, ERROR
}
