// =============================================================================
// Arquivo: com.marin.catfeina.features.marcador.MarcadorBottomSheet.kt
// Descrição: Conteúdo da BottomSheet para selecionar um slot de marcador.
// =============================================================================
package com.marin.catfeina.features.marcador

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.Marcador

@Composable
fun MarcadorBottomSheetContent(
    marcadores: List<Marcador>,
    onSlotSelected: (Int) -> Unit
) {
    val todosOsSlots = (1..10).map {
        val marcadorExistente = marcadores.find { m -> m.slotId == it }
        marcadorExistente ?: Marcador(slotId = it, tituloDisplay = null, rotaNavegacao = null, dataSalvo = null)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Salvar Atalho", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(todosOsSlots, key = { it.slotId }) { marcador ->
                MarcadorSlotItem(marcador = marcador, onSlotSelected = onSlotSelected)
            }
        }
    }
}

@Composable
private fun MarcadorSlotItem(
    marcador: Marcador,
    onSlotSelected: (Int) -> Unit
) {
    val isSlotOcupado = marcador.tituloDisplay != null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSlotSelected(marcador.slotId) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isSlotOcupado) Icones.MarcadorCheio else Icones.MarcadorVazio,
            contentDescription = "Slot ${marcador.slotId}",
            tint = if (isSlotOcupado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Slot ${marcador.slotId}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = marcador.tituloDisplay ?: "Vazio",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
