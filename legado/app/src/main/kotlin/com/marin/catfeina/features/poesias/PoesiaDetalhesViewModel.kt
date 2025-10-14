// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaDetalhesViewModel.kt
// Descrição: ViewModel para a tela de detalhes de uma Poesia.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.AppDestinations
import com.marin.catfeina.AppDestinationsArgs
import com.marin.catfeina.core.SoundService
import com.marin.catfeina.core.data.entities.Marcador
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.entities.TipoConteudo
import com.marin.catfeina.core.data.repository.HistoricoRepository
import com.marin.catfeina.core.data.repository.MarcadorRepository
import com.marin.catfeina.core.data.repository.PoesiaRepository
import com.marin.catfeina.core.di.CatshitoService
import com.marin.catfeina.core.di.EventoCatshito
import com.marin.catfeina.core.formatador.parser.ParserTextoFormatado
import com.marin.catfeina.core.tts.CatfeinaTtsService
import com.marin.catfeina.core.tts.TtsStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PoesiaDetalhesViewModel @Inject constructor(
    private val poesiaRepository: PoesiaRepository,
    private val historicoRepository: HistoricoRepository,
    private val marcadorRepository: MarcadorRepository,
    private val ttsService: CatfeinaTtsService,
    private val soundService: SoundService,
    private val catshitoService: CatshitoService,
    val parserTexto: ParserTextoFormatado,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val poesiaId: StateFlow<Long> =
        savedStateHandle.getStateFlow(AppDestinationsArgs.POESIA_ID_ARG, 0L)

    val poesiaSelecionada: StateFlow<Poesia?> = poesiaId.flatMapLatest { id ->
        if (id > 0L) poesiaRepository.getPoesiaById(id) else flowOf(null)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val marcadores: StateFlow<List<Marcador>> = marcadorRepository.getTodosOsMarcadores()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(PoesiaDetalhesUiState())
    val uiState = _uiState.asStateFlow()

    val ttsStatus: StateFlow<TtsStatus> = ttsService.ttsStatus

    init {
        poesiaSelecionada.onEach { poesia ->
            poesia?.let {
                _uiState.update { it.copy(textoAnotacao = poesia.notaUsuario ?: "") }
                historicoRepository.adicionarVisita(
                    tipo = TipoConteudo.POESIA,
                    id = it.id,
                    titulo = it.titulo,
                    imagem = it.imagem
                )
                // Processa o evento de leitura geral
                catshitoService.processarEvento(EventoCatshito.LEITURA_POESIA)
            }
        }.launchIn(viewModelScope)
    }

    fun onUiEvent(event: PoesiaDetalhesEvent) {
        when (event) {
            is PoesiaDetalhesEvent.SalvarPoesia -> salvarPoesia(event.poesia, event.isFavoritoToggle)
            is PoesiaDetalhesEvent.MarcarComoLido -> marcarComoLido()
            is PoesiaDetalhesEvent.AnotacaoChanged -> _uiState.update { it.copy(textoAnotacao = event.texto) }
            is PoesiaDetalhesEvent.SalvarAnotacao -> salvarAnotacao()
            is PoesiaDetalhesEvent.OnMarcadorClick -> salvarMarcador(event.slotId)
            is PoesiaDetalhesEvent.ToggleMarcadorSheet -> _uiState.update { it.copy(isMarcadorSheetVisible = event.isVisible) }
            PoesiaDetalhesEvent.TocarOuPausarTTS -> tocarOuPausarLeitura()
            PoesiaDetalhesEvent.PararTTS -> pararLeitura()
            PoesiaDetalhesEvent.TocarOuPausarSomAmbiente -> tocarOuPausarSomAmbiente()
            PoesiaDetalhesEvent.PararSomAmbiente -> pararSomAmbiente()
        }
    }

    private fun marcarComoLido() {
        viewModelScope.launch {
            catshitoService.processarEvento(EventoCatshito.PRIMEIRA_LEITURA)
        }
    }

    private fun tocarOuPausarSomAmbiente() {
        _uiState.update { it.copy(isSomAmbienteTocando = !it.isSomAmbienteTocando) }
        if (uiState.value.isSomAmbienteTocando) {
            // TODO: Colocar o som do ambiente aqui
        } else {
            soundService.pause()
        }
    }

    private fun pararSomAmbiente() {
        soundService.stop()
        _uiState.update { it.copy(isSomAmbienteTocando = false) }
    }

    private fun salvarPoesia(poesia: Poesia?, isFavoritoToggle: Boolean) {
        if (poesia == null) return
        viewModelScope.launch {
            poesiaRepository.salvarPoesia(poesia)
            if (isFavoritoToggle && poesia.isFavorito) {
                catshitoService.processarEvento(EventoCatshito.POESIA_FAVORITA)
            }
        }
    }

    private fun salvarAnotacao() {
        val poesiaAtual = poesiaSelecionada.value ?: return
        val poesiaAtualizada = poesiaAtual.copy(notaUsuario = uiState.value.textoAnotacao)
        salvarPoesia(poesiaAtualizada, isFavoritoToggle = false)
    }

    private fun salvarMarcador(slotId: Int) {
        val poesia = poesiaSelecionada.value ?: return
        viewModelScope.launch {
            marcadorRepository.salvarMarcador(
                slotId = slotId,
                titulo = poesia.titulo,
                rota = "${AppDestinations.POESIA_DETALHES_ROUTE}/${poesia.id}"
            )
            _uiState.update { it.copy(isMarcadorSheetVisible = false) }
        }
    }

    private fun tocarOuPausarLeitura() {
        when (ttsStatus.value) {
            TtsStatus.SPEAKING -> ttsService.pausar()
            TtsStatus.PAUSED -> ttsService.continuar()
            else -> {
                val poesia = poesiaSelecionada.value ?: return
                val titulo = poesia.titulo.let { if (it.endsWith(".")) it else "$it." }
                val corpoPoesia = parserTexto.extrairTextoPuroParaTTS(poesia.texto)
                ttsService.falar("$titulo $corpoPoesia")
            }
        }
    }

    private fun pararLeitura() {
        ttsService.parar()
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.desligar()
        soundService.release()
    }
}

data class PoesiaDetalhesUiState(
    val textoAnotacao: String = "",
    val isMarcadorSheetVisible: Boolean = false,
    val isSomAmbienteTocando: Boolean = false
)

sealed interface PoesiaDetalhesEvent {
    data class SalvarPoesia(val poesia: Poesia?, val isFavoritoToggle: Boolean = false) : PoesiaDetalhesEvent
    object MarcarComoLido : PoesiaDetalhesEvent
    data class AnotacaoChanged(val texto: String) : PoesiaDetalhesEvent
    data class ToggleMarcadorSheet(val isVisible: Boolean) : PoesiaDetalhesEvent
    data class OnMarcadorClick(val slotId: Int) : PoesiaDetalhesEvent
    object SalvarAnotacao : PoesiaDetalhesEvent
    object TocarOuPausarTTS : PoesiaDetalhesEvent
    object PararTTS : PoesiaDetalhesEvent
    object TocarOuPausarSomAmbiente : PoesiaDetalhesEvent
    object PararSomAmbiente : PoesiaDetalhesEvent
}
