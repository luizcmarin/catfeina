// =============================================================================
// Arquivo: com.marin.catfeina.features.marcador.MarcadorViewModel.kt
// Descrição: ViewModel para a tela de Marcadores.
// =============================================================================
package com.marin.catfeina.features.marcador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.Marcador
import com.marin.catfeina.core.data.repository.MarcadorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarcadorViewModel @Inject constructor(
    private val marcadorRepository: MarcadorRepository
) : ViewModel() {

    /**
     * Expõe um StateFlow com a lista completa de todos os marcadores (slots de 1 a 10).
     * O repositório já nos dará a lista completa.
     */
    val marcadoresState: StateFlow<List<Marcador>> = marcadorRepository.getTodosOsMarcadores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Manipula eventos da UI para a tela de marcadores.
     */
    fun onUiEvent(event: MarcadorEvent) {
        when (event) {
            is MarcadorEvent.LimparMarcador -> {
                viewModelScope.launch {
                    marcadorRepository.limparMarcador(event.slotId)
                }
            }
        }
    }
}

/**
 * Define os eventos que a UI pode enviar para o ViewModel da tela de Marcadores.
 */
sealed interface MarcadorEvent {
    data class LimparMarcador(val slotId: Int) : MarcadorEvent
}
