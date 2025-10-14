// =============================================================================
// Arquivo: com.marin.catfeina.features.atelier.AtelierListScreen.kt
// Descrição: Tela de listagem de notas do Atelier.
// =============================================================================
package com.marin.catfeina.features.atelier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.AtelierEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val ATELIER_EDIT_ROUTE = "atelier_edit"
const val ATELIER_EDIT_WITH_ARG_ROUTE = "$ATELIER_EDIT_ROUTE/{$ATELIER_ITEM_ID_ARG}"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtelierScreen( // Renomeado para AtelierScreen para consistência
    navController: NavController,
    viewModel: AtelierListViewModel = hiltViewModel()
) {
    val itens by viewModel.itensDoAtelier.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Atelier") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(ATELIER_EDIT_ROUTE) }) {
                Icon(Icones.Mais, contentDescription = "Nova Nota")
            }
        }
    ) { paddingValues ->
        if (itens.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Seu atelier está vazio. Toque em '+' para criar uma nova nota.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(itens, key = { it.id }) { item ->
                    AtelierListItem(item = item) {
                        navController.navigate("$ATELIER_EDIT_ROUTE/${item.id}")
                    }
                }
            }
        }
    }
}

@Composable
private fun AtelierListItem(item: AtelierEntity, onClick: () -> Unit) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.titulo, style = MaterialTheme.typography.titleMedium)
            Text(
                text = item.conteudo,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Atualizado em: ${formatter.format(Date(item.dataAtualizacao))}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
