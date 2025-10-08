// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaDetalhesViewModel.kt
// Descrição: ViewModel para a tela de detalhes de uma Poesia.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.AppDestinationsArgs
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.repository.PoesiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PoesiaDetalhesViewModel @Inject constructor(
    private val poesiaRepository: PoesiaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val poesiaId: StateFlow<Long> = savedStateHandle.getStateFlow(AppDestinationsArgs.POESIA_ID_ARG, 0L)

    val poesiaSelecionada: StateFlow<Poesia?> = poesiaId.flatMapLatest { id ->
        if (id > 0L) {
            poesiaRepository.getPoesiaById(id)
        } else {
            flowOf(null)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun onPoesiaEvent(poesia: Poesia?) {
        if (poesia == null) return
        viewModelScope.launch {
            poesiaRepository.salvarPoesia(poesia)
        }
    }
}
