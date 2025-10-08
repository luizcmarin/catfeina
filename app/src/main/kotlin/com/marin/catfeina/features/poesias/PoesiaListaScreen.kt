// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaListaScreen.kt
// Descrição: Tela principal que exibe a lista de poesias.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.marin.catfeina.core.data.entities.Poesia

@Composable
fun PoesiaListaScreen(
    viewModel: PoesiaListaViewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
                "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
            }, null),
    onPoesiaClick: (Long) -> Unit
) {
    // Coleta a lista de poesias do ViewModel como um State.
    // A UI será recomposta automaticamente sempre que esta lista mudar.
    val poesias by viewModel.poesias.collectAsState()

    PoesiasList(
        poesias = poesias,
        onPoesiaEvent = viewModel::onPoesiaEvent,
        onCardClick = onPoesiaClick
    )
}

@Composable
private fun PoesiasList(
    poesias: List<Poesia>,
    onPoesiaEvent: (Poesia) -> Unit,
    onCardClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = poesias,
            key = { poesia -> poesia.id }
        ) { poesia ->
            PoesiaCardItem(
                poesia = poesia,
                onPoesiaEvent = onPoesiaEvent,
                onCardClick = onCardClick
            )
        }
    }
}
