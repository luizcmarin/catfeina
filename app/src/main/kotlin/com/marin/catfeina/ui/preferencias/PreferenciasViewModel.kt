/*
 * Arquivo: com.marin.catfeina.ui.preferencias.PreferenciasViewModel.kt
 * Salva preferencias de usuário no DataStore.
 */
package com.marin.catfeina.ui.preferencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferenciasViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {

    // Converte o Flow do repositório em um StateFlow para ser observado pela UI do Compose.
    val isDarkMode: StateFlow<Boolean> = userPreferencesRepository.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // Função para a UI chamar e atualizar a preferência.
    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkMode(isDarkMode)
        }
    }

    // Factory para criar o ViewModel com o repositório como dependência.
    companion object {
        fun provideFactory(
            userPreferencesRepository: UserPreferencesRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PreferenciasViewModel::class.java)) {
                    return PreferenciasViewModel(userPreferencesRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
