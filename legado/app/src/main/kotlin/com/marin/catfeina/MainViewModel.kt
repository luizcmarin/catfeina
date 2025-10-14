// =============================================================================
// Arquivo: com.marin.catfeina.MainViewModel.kt
// Descrição: ViewModel principal da aplicação, responsável por decisões globais de navegação.
// =============================================================================
package com.marin.catfeina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = true,
    val onboardingCompleto: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = userPreferencesRepository.onboardingCompleto
        .map { onboardingCompleto ->
            MainUiState(isLoading = false, onboardingCompleto = onboardingCompleto)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState(isLoading = true)
        )
}
