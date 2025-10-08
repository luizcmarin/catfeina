// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaListaViewModel.kt
// Descrição: ViewModel para a tela da lista de Poesias.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.repository.PoesiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoesiaListaViewModel @Inject constructor(
    private val poesiaRepository: PoesiaRepository
) : ViewModel() {

    val poesias: StateFlow<List<Poesia>> = poesiaRepository.getPoesiasCompletas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onPoesiaEvent(poesia: Poesia) {
        viewModelScope.launch {
            poesiaRepository.salvarPoesia(poesia)
        }
    }
}
