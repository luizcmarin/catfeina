// =============================================================================
// Arquivo: com.marin.catfeina.features.inicio.InicioViewModel.kt
// Descrição: ViewModel para a tela de Início.
// =============================================================================
package com.marin.catfeina.features.inicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.repository.PoesiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class InicioUiState(
    val isLoading: Boolean = true,
    val poesiaAleatoria: Poesia? = null,
    val poesiasFavoritas: List<Poesia> = emptyList(),
    val todasAsPoesias: List<Poesia> = emptyList()
)

@HiltViewModel
class InicioViewModel @Inject constructor(
    poesiaRepository: PoesiaRepository
) : ViewModel() {

    private val _poesiaAleatoria = poesiaRepository.getPoesiaAleatoria()
    private val _poesiasFavoritas = poesiaRepository.getPoesiasFavoritas()
    private val _todasAsPoesias = poesiaRepository.getPoesiasCompletas()

    val uiState: StateFlow<InicioUiState> = combine(
        _poesiaAleatoria, _poesiasFavoritas, _todasAsPoesias
    ) { aleatoria, favoritas, todas ->
        InicioUiState(
            isLoading = false, // Dados carregados
            poesiaAleatoria = aleatoria,
            poesiasFavoritas = favoritas,
            todasAsPoesias = todas
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InicioUiState(isLoading = true)
    )
}
