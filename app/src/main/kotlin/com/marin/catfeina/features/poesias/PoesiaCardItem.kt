// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaCardItem.kt
// Descrição: Composable que exibe um único item de poesia em um card.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.Icones

@Composable
fun PoesiaCardItem(
    poesia: Poesia,
    onPoesiaEvent: (Poesia) -> Unit,
    onCardClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            // Ação de clique no card inteiro para navegar para os detalhes
            .clickable { onCardClick(poesia.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                // CORREÇÃO: Acessa 'titulo' diretamente
                text = poesia.titulo,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                // CORREÇÃO: Acessa 'texto' diretamente
                text = poesia.texto.replace("\n", " "),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                // Botão para favoritar
                IconButton(
                    onClick = {
                        // CORREÇÃO: Cria uma cópia da poesia, atualizando os campos diretamente
                        val poesiaAtualizada = poesia.copy(
                            isFavorito = !poesia.isFavorito,
                            dataFavoritado = if (!poesia.isFavorito) System.currentTimeMillis() else null
                        )
                        // Dispara o evento com a poesia atualizada para o ViewModel
                        onPoesiaEvent(poesiaAtualizada)
                    }
                ) {
                    Icon(
                        // CORREÇÃO: Verifica 'poesia.isFavorito' diretamente
                        imageVector = if (poesia.isFavorito) Icones.Favorito else Icones.DesmarcaFavorito,
                        contentDescription = "Favoritar",
                        // CORREÇÃO: Verifica 'poesia.isFavorito' diretamente para definir a cor
                        tint = if (poesia.isFavorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
