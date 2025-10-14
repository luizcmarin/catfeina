package com.marin.catfeina.features.poesias

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.Poesia

@Composable
fun PoesiaCardItem(
    poesia: Poesia,
    onFavoritoToggle: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            poesia.imagem?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagem da poesia: ${poesia.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(64.dp)
                        .width(64.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = poesia.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = onFavoritoToggle) {
                Icon(
                    imageVector = if (poesia.isFavorito) Icones.FavoritoCheio else Icones.FavoritoVazio,
                    contentDescription = if (poesia.isFavorito) "Desfavoritar" else "Favoritar"
                )
            }
        }
    }
}
