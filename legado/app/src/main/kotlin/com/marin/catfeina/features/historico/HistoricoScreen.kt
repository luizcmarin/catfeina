// =============================================================================
// Arquivo: com.marin.catfeina.features.historico.HistoricoScreen.kt
// Descrição: Tela que exibe o histórico de visitas do usuário.
// =============================================================================
package com.marin.catfeina.features.historico

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.marin.catfeina.AppDestinations
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.HistoricoVisita
import com.marin.catfeina.core.data.entities.TipoConteudo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoScreen(
    navController: NavController,
    viewModel: HistoricoViewModel = hiltViewModel()
) {
    val historico by viewModel.historicoState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico de Visitas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onUiEvent(HistoricoEvent.LimparHistorico) }) {
                        Icon(Icones.Deletar, contentDescription = "Limpar Histórico")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (historico.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Seu histórico de visitas está vazio.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(historico, key = { it.id }) { visita ->
                    HistoricoItemCard(visita = visita) {
                        val route = when (visita.tipoConteudo) {
                            TipoConteudo.POESIA -> "${AppDestinations.POESIA_DETALHES_ROUTE}/${visita.conteudoId}"
                            TipoConteudo.PERSONAGEM -> "${AppDestinations.PERSONAGEM_DETALHES_ROUTE}/${visita.conteudoId}"
                            else -> null // Não navegar para informativos por enquanto
                        }
                        route?.let { navController.navigate(it) }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoricoItemCard(visita: HistoricoVisita, onClick: () -> Unit) {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = visita.imagemDisplay,
                contentDescription = "Imagem de ${visita.tituloDisplay}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = visita.tituloDisplay,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatter.format(Date(visita.dataVisita)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
