// =============================================================================
// Arquivo: com.marin.catfeina.features.mascote.MascoteComposable.kt
// Descrição: Composable que renderiza o mascote Catshito e suas frases.
// Abordagem: Usa AndroidView para garantir compatibilidade máxima.
// =============================================================================
package com.marin.catfeina.features.mascote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.marin.catfeina.R

/**
 * Renderiza a animação do mascote Catshito e uma frase associada.
 * Esta versão utiliza AndroidView para encapsular a RiveAnimationView, garantindo
 * robustez e compatibilidade com o sistema de Views do Android.
 *
 * @param nivelMascote O nível atual do mascote, usado para selecionar o artboard correto.
 * @param frase A frase que o mascote irá "dizer".
 * @param onMascoteClick Callback invocado quando o mascote é clicado.
 * @param modifier Modificador para customização do layout.
 */
@Composable
fun MascoteComposable(
    nivelMascote: NivelMascote,
    frase: String,
    onMascoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val artboardName = remember(nivelMascote) {
        when (nivelMascote) {
            NivelMascote.FILHOTE -> "Filhote"
            NivelMascote.JOVEM -> "Jovem"
            NivelMascote.EXPLORADOR -> "Explorador"
            NivelMascote.MESTRE -> "Mestre"
            NivelMascote.LENDARIO -> "Lendario"
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            modifier = Modifier.size(250.dp),
            factory = {
                context ->
                RiveAnimationView(context).apply {
                    // Carrega o recurso, o artboard inicial e a state machine
                    setRiveResource(
                        resId = R.raw.catshito,
                        artboardName = artboardName,
                        stateMachineName = "CatshitoStateMachine",
                        autoplay = true
                    )
                }
            },
            update = { view ->
                // Garante que o artboard seja atualizado se o nível do mascote mudar
                if (view.artboardName != artboardName) {
                    view.artboardName = artboardName
                }
                // Atualiza a descrição de conteúdo para acessibilidade
                view.contentDescription = "Mascote Catshito, nível ${nivelMascote.name.lowercase()}"

                // Define o listener de clique, garantindo que o lambda mais recente seja usado
                view.setOnClickListener {
                    onMascoteClick()
                    view.fireState("CatshitoStateMachine", "toque")
                }
            }
        )

        Card(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = "\"${frase}\"",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
