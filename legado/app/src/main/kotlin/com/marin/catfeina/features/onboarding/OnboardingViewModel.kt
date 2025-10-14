// =============================================================================
// Arquivo: com.marin.catfeina.features.onboarding.OnboardingViewModel.kt
// Descrição: ViewModel para a tela de Onboarding.
// =============================================================================
package com.marin.catfeina.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun onOnboardingComplete() {
        viewModelScope.launch {
            userPreferencesRepository.setOnboardingCompleto(true)
        }
    }
}
