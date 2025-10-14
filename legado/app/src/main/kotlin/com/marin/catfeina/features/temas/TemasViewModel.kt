package com.marin.catfeina.features.temas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.theme.GerenciadorTemas
import com.marin.catfeina.core.theme.ThemeModel
import com.marin.catfeina.core.theme.ThemeModelKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TemasUiState(
    val temasDisponiveis: Map<ThemeModelKey, ThemeModel> = emptyMap(),
    val temaAtualKey: ThemeModelKey = ThemeModelKey.VERAO, // Padrão VERAO
    val isDarkMode: Boolean = false
)

@HiltViewModel
class TemasViewModel @Inject constructor(
    private val gerenciadorTemas: GerenciadorTemas
) : ViewModel() {

    val uiState: StateFlow<TemasUiState> = combine(
        gerenciadorTemas.currentThemeKey,
        gerenciadorTemas.isDarkMode
    ) { themeKey, isDark ->
        TemasUiState(
            temasDisponiveis = gerenciadorTemas.getAvailableThemes(),
            temaAtualKey = themeKey,
            isDarkMode = isDark
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        // Valor inicial usa o estado síncrono do GerenciadorTemas e o padrão VERAO.
        initialValue = TemasUiState(
            temasDisponiveis = gerenciadorTemas.getAvailableThemes(),
            temaAtualKey = ThemeModelKey.VERAO
        )
    )

    fun onThemeSelected(key: ThemeModelKey) {
        viewModelScope.launch {
            gerenciadorTemas.setTheme(key)
        }
    }

    fun onDarkModeChange(isDarkMode: Boolean) {
        viewModelScope.launch {
            gerenciadorTemas.setDarkMode(isDarkMode)
        }
    }
}
