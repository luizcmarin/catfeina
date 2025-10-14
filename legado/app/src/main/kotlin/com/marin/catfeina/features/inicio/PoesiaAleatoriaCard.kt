// =============================================================================
// Arquivo: com.marin.catfeina.features.inicio.PoesiaAleatoriaCard.kt
// Descrição: Card para exibir uma poesia em destaque na tela inicial.
// =============================================================================
package com.marin.catfeina.features.inicio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.marin.catfeina.core.data.entities.Poesia

@Composable
fun PoesiaAleatoriaCard(
    poesia: Poesia,
    onNavigate: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigate(poesia.id) }
    ) {
        Column {
            poesia.imagem?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagem da poesia: ${poesia.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = poesia.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = poesia.textoBase, // Usando textoBase para um resumo
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
