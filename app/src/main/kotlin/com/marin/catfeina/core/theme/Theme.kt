package com.marin.catfeina.core.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun CatfeinaTheme(
    GerenciadorTemas: GerenciadorTemas,
    content: @Composable () -> Unit
) {
    // Coleta o ColorScheme reativo do GerenciadorTemas
    val colorScheme by GerenciadorTemas.colorScheme.collectAsState(
        // Valor inicial para evitar tela branca, usando o tema padrão VERAO.
        initial = GerenciadorTemas.getAvailableThemes().getValue(ThemeModelKey.VERAO).colorPalette.lightModeColors
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            // Este valor deve ser ajustado dinamicamente com base no tema.
            // Por enquanto, está fixo, mas pode ser aprimorado no futuro.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
//        typography = Typography,
        content = content
    )
}
