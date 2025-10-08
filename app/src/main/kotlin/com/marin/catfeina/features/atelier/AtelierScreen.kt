// =============================================================================
// Arquivo: com.marin.catfeina.features.atelier.AtelierScreen.kt
// Descrição: Tela que exibe a lista de itens do atelier.
// =============================================================================
package com.marin.catfeina.features.atelier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.marin.catfeina.core.data.entities.AtelierEntity

@Composable
fun AtelierScreen(
    viewModel: AtelierViewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
                "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
            }, null)
) {
    val itens by viewModel.itens.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = itens,
            key = { item -> item.id }
        ) { item ->
            AtelierCard(item = item)
        }
    }
}

@Composable
private fun AtelierCard(
    item: AtelierEntity,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.titulo,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
