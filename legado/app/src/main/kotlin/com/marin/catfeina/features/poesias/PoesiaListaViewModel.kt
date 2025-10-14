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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PoesiaListUiState(
    val isLoading: Boolean = true,
    val poesias: List<Poesia> = emptyList()
)

@HiltViewModel
class PoesiaListaViewModel @Inject constructor(
    private val poesiaRepository: PoesiaRepository
) : ViewModel() {

    val uiState: StateFlow<PoesiaListUiState> = poesiaRepository.getPoesiasCompletas()
        .map { PoesiaListUiState(isLoading = false, poesias = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PoesiaListUiState(isLoading = true)
        )

    fun onFavoritoToggle(poesia: Poesia) {
        viewModelScope.launch {
            val poesiaAtualizada = poesia.copy(isFavorito = !poesia.isFavorito)
            poesiaRepository.salvarPoesia(poesiaAtualizada)
        }
    }
}
