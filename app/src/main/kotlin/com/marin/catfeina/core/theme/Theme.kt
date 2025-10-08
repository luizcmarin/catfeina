package com.marin.catfeina.core.theme

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView

@Composable
fun CatfeinaTheme(
    gerenciadorTemas: GerenciadorTemas,
    content: @Composable () -> Unit
) {
    // Coleta o ColorScheme reativo do GerenciadorTemas.
    val colorScheme by gerenciadorTemas.colorScheme.collectAsState(
        initial = gerenciadorTemas.getAvailableThemes().getValue(ThemeModelKey.VERAO).colorPalette.lightModeColors
    )

    // Coleta o estado de tema escuro. isDarkMode = true (escuro), isDarkMode = false (claro).
    val isDarkMode by gerenciadorTemas.isDarkMode.collectAsState(initial = false)

    val view = LocalView.current
    if (!view.isInEditMode) {
        // Usamos DisposableEffect para garantir que o controle dos ícones seja
        // aplicado sempre que o tema mudar e limpo quando o Composable sair da tela.
        DisposableEffect(isDarkMode) {
            val activity = view.context as ComponentActivity

            activity.enableEdgeToEdge(
                // Define o estilo dos ícones da barra de status (hora, bateria).
                statusBarStyle = if (isDarkMode) {
                    // Tema escuro -> Fundo escuro -> Ícones claros.
                    SystemBarStyle.dark(
                        scrim = android.graphics.Color.TRANSPARENT
                    )
                } else {
                    // Tema claro -> Fundo claro -> Ícones escuros.
                    SystemBarStyle.light(
                        scrim = android.graphics.Color.TRANSPARENT,
                        darkScrim = android.graphics.Color.TRANSPARENT
                    )
                },
                // Define o estilo dos ícones da barra de navegação (botões).
                navigationBarStyle = if (isDarkMode) {
                    // Tema escuro -> Fundo escuro -> Ícones claros.
                    SystemBarStyle.dark(
                        scrim = android.graphics.Color.TRANSPARENT
                    )
                } else {
                    // Tema claro -> Fundo claro -> Ícones escuros.
                    SystemBarStyle.light(
                        scrim = android.graphics.Color.TRANSPARENT,
                        darkScrim = android.graphics.Color.TRANSPARENT
                    )
                }
            )
            onDispose {}
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
//        typography = Typography,
        content = content
    )
}
