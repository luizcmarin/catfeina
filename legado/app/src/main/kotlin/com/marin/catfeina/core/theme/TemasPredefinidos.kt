package com.marin.catfeina.core.theme

import com.marin.catfeina.ui.theme.verao.veraoLightScheme
import com.marin.catfeina.ui.theme.verao.veraoDarkScheme
import com.marin.catfeina.ui.theme.outono.outonoLightScheme
import com.marin.catfeina.ui.theme.outono.outonoDarkScheme
import com.marin.catfeina.ui.theme.inverno.invernoLightScheme
import com.marin.catfeina.ui.theme.inverno.invernoDarkScheme
import com.marin.catfeina.ui.theme.primavera.primaveraLightScheme
import com.marin.catfeina.ui.theme.primavera.primaveraDarkScheme

/**
 * Objeto Singleton que fornece um mapa de todos os temas disponíveis na aplicação.
 * Centraliza a definição dos temas, facilitando a manutenção.
 * ESTA É A VERSÃO CORRETA E FINAL: Ela usa os ColorSchemes dos seus arquivos gerados.
 */
object TemasPredefinidos {

    fun get(): Map<ThemeModelKey, ThemeModel> {
        return mapOf(
            ThemeModelKey.VERAO to ThemeModel(
                colorPalette = ColorPalette(
                    lightModeColors = veraoLightScheme,
                    darkModeColors = veraoDarkScheme
                )
            ),
            ThemeModelKey.OUTONO to ThemeModel(
                colorPalette = ColorPalette(
                    lightModeColors = outonoLightScheme,
                    darkModeColors = outonoDarkScheme
                )
            ),
            ThemeModelKey.INVERNO to ThemeModel(
                colorPalette = ColorPalette(
                    lightModeColors = invernoLightScheme,
                    darkModeColors = invernoDarkScheme
                )
            ),
            ThemeModelKey.PRIMAVERA to ThemeModel(
                colorPalette = ColorPalette(
                    lightModeColors = primaveraLightScheme,
                    darkModeColors = primaveraDarkScheme
                )
            )
        )
    }
}
