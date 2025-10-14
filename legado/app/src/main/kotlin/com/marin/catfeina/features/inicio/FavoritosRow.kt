// =============================================================================
// Arquivo: com.marin.catfeina.features.inicio.FavoritosRow.kt
// Descrição: Linha horizontal para exibir poesias favoritas na tela inicial.
// =============================================================================
package com.marin.catfeina.features.inicio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun FavoritosRow(
    favoritos: List<Poesia>,
    onNavigate: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(favoritos, key = { it.id }) { poesia ->
            FavoritoItem(poesia = poesia, onNavigate = onNavigate)
        }
    }
}

@Composable
private fun FavoritoItem(
    poesia: Poesia,
    onNavigate: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable { onNavigate(poesia.id) }
    ) {
        Column {
            AsyncImage(
                model = poesia.imagem,
                contentDescription = "Imagem da poesia: ${poesia.titulo}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                text = poesia.titulo,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
