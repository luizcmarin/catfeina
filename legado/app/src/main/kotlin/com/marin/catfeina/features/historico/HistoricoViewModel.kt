// =============================================================================
// Arquivo: com.marin.catfeina.features.historico.HistoricoViewModel.kt
// Descrição: ViewModel para a tela de Histórico de Visitas.
// =============================================================================
package com.marin.catfeina.features.historico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.HistoricoVisita
import com.marin.catfeina.core.data.repository.HistoricoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val historicoRepository: HistoricoRepository
) : ViewModel() {

    /**
     * Expõe um StateFlow com a lista de itens do histórico.
     * O operador stateIn converte o Flow frio do repositório em um Flow quente,
     * compartilhando o resultado com múltiplos coletores e mantendo o último valor.
     */
    val historicoState: StateFlow<List<HistoricoVisita>> = historicoRepository.getHistoricoCompleto()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Manipula eventos da UI para a tela de histórico.
     */
    fun onUiEvent(event: HistoricoEvent) {
        when (event) {
            HistoricoEvent.LimparHistorico -> {
                viewModelScope.launch {
                    historicoRepository.limparHistorico()
                }
            }
        }
    }
}

/**
 * Define os eventos que a UI pode enviar para o ViewModel da tela de Histórico.
 */
sealed interface HistoricoEvent {
    object LimparHistorico : HistoricoEvent
}
