// =============================================================================
// Arquivo: com.marin.catfeina.features.personagens.PersonagemScreen.kt
// Descrição: Tela que exibe a lista de personagens.
// =============================================================================
package com.marin.catfeina.features.personagens

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.marin.catfeina.core.data.entities.PersonagemEntity

@Composable
fun PersonagensScreen(
    viewModel: PersonagemViewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
                "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
            }, null),
    onPersonagemClick: (Long) -> Unit // Callback para a navegação
) {
    val personagens by viewModel.personagens.collectAsStateWithLifecycle(initialValue = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = personagens,
            key = { personagem -> personagem.id }
        ) { personagem ->
            PersonagemCard(
                personagem = personagem,
                onClick = { onPersonagemClick(personagem.id) } // Passa o ID no clique
            )
        }
    }
}

@Composable
private fun PersonagemCard(
    personagem: PersonagemEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Torna o card clicável
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = personagem.nome,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
