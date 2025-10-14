// ===================================================================================
// Arquivo: com.marin.catfeina.core.rive.RiveLoadingIndicator.kt
// Descrição: Composable reutilizável para exibir uma animação de loading do Rive.
//
// Propósito:
// Este componente encapsula a lógica para carregar e exibir uma animação vetorial
// do Rive, servindo como um indicador de carregamento padronizado em todo o
// aplicativo. Ele utiliza o arquivo "cats_loader.riv" dos recursos.
// ===================================================================================

package com.marin.catfeina.core.rive

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import com.marin.catfeina.R

/**
 * Exibe uma animação de loading a partir de um arquivo Rive.
 *
 * Este Composable utiliza a interoperabilidade do `AndroidView` para renderizar
 * uma `RiveAnimationView` (do artefato `rive-android`) dentro do Jetpack Compose.
 *
 * @param modifier O modificador a ser aplicado a este layout.
 */
@Composable
fun RiveLoadingIndicator(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            // Cria a View do Rive programaticamente
            RiveAnimationView(context).apply {
                // Define o recurso Rive a ser carregado
                setRiveResource(R.raw.cats_loader)
                // Configurações da animação
                artboardName = "Paw" // <<<<<<< CORREÇÃO AQUI >>>>>>> Nome do artboard corrigido de "New Artboard" para "Paw"
                fit = Fit.CONTAIN
                play(loop = Loop.LOOP)
            }
        },
        modifier = modifier
    )
}
