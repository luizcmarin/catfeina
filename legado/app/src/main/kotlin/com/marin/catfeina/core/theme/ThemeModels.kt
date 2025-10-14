package com.marin.catfeina.core.theme

import androidx.compose.material3.ColorScheme

/**
 * Enum para as chaves de identificação dos temas.
 * Garante segurança de tipo ao selecionar e salvar temas.
 */
enum class ThemeModelKey {
    VERAO,  // Tema padrão
    OUTONO,
    INVERNO,
    PRIMAVERA
}

/**
 * Representa um único tema, contendo suas paletas de cores.
 */
data class ThemeModel(
    val colorPalette: ColorPalette
)

/**
 * Contém as paletas de cores para os modos claro e escuro de um tema.
 */
data class ColorPalette(
    val lightModeColors: ColorScheme,
    val darkModeColors: ColorScheme
)

