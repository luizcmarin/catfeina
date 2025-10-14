// =============================================================================
// Arquivo: com.marin.catfeina.features.marcador.MarcadorScreen.kt
// Descrição: Tela que exibe os slots de marcadores (atalhos) salvos.
// =============================================================================
package com.marin.catfeina.features.marcador

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.Marcador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarcadorScreen(
    navController: NavController,
    viewModel: MarcadorViewModel = hiltViewModel()
) {
    val marcadores by viewModel.marcadoresState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marcadores") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Garante que sempre teremos 10 slots, preenchendo os vazios
        val todosOsSlots = (1..10).map {
            val marcadorExistente = marcadores.find { m -> m.slotId == it }
            marcadorExistente ?: Marcador(slotId = it, tituloDisplay = null, rotaNavegacao = null, dataSalvo = null)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(todosOsSlots, key = { it.slotId }) { marcador ->
                MarcadorItem(
                    marcador = marcador,
                    onNavigate = { rota -> navController.navigate(rota) },
                    onClear = { viewModel.onUiEvent(MarcadorEvent.LimparMarcador(marcador.slotId)) }
                )
            }
        }
    }
}

@Composable
private fun MarcadorItem(
    marcador: Marcador,
    onNavigate: (String) -> Unit,
    onClear: () -> Unit
) {
    val isSlotOcupado = marcador.rotaNavegacao != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isSlotOcupado) {
                marcador.rotaNavegacao?.let { onNavigate(it) }
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isSlotOcupado) Icones.MarcadorCheio else Icones.MarcadorVazio,
                contentDescription = "Slot ${marcador.slotId}",
                tint = if (isSlotOcupado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
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
            if (isSlotOcupado) {
                IconButton(onClick = onClear) {
                    Icon(Icones.Deletar, contentDescription = "Limpar Slot")
                }
            }
        }
    }
}
