// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaListaScreen.kt
// Descrição: Tela principal que exibe a lista de poesias.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.marin.catfeina.core.data.entities.Poesia

@Composable
fun PoesiaListaScreen(
    viewModel: PoesiaListaViewModel = hiltViewModel(),
    onPoesiaClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        PoesiasList(
            poesias = uiState.poesias,
            onFavoritoToggle = viewModel::onFavoritoToggle,
            onCardClick = onPoesiaClick
        )
    }
}

@Composable
private fun PoesiasList(
    poesias: List<Poesia>,
    onFavoritoToggle: (Poesia) -> Unit,
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
                onFavoritoToggle = { onFavoritoToggle(poesia) },
                onCardClick = { onCardClick(poesia.id) }
            )
        }
    }
}
