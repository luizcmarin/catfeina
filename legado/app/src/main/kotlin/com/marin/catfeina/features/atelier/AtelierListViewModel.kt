// =============================================================================
// Arquivo: com.marin.catfeina.features.atelier.AtelierListViewModel.kt
// Descrição: ViewModel para a tela de listagem do Atelier.
// =============================================================================
package com.marin.catfeina.features.atelier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.AtelierEntity
import com.marin.catfeina.core.data.repository.AtelierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AtelierListViewModel @Inject constructor(
    private val atelierRepository: AtelierRepository
) : ViewModel() {

    val itensDoAtelier: StateFlow<List<AtelierEntity>> = atelierRepository.getAllItens()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onEvent(event: AtelierListEvent) {
        when (event) {
            is AtelierListEvent.DeletarItem -> {
                viewModelScope.launch {
                    atelierRepository.deletarItem(event.item)
                }
            }
        }
    }
}

sealed interface AtelierListEvent {
    data class DeletarItem(val item: AtelierEntity) : AtelierListEvent
}
