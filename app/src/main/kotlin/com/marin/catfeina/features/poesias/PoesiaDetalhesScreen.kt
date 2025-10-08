// =============================================================================
// Arquivo: com.marin.catfeina.features.poesias.PoesiaDetalhesScreen.kt
// Descrição: Tela que exibe os detalhes completos de uma única poesia.
// =============================================================================
package com.marin.catfeina.features.poesias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.Poesia

// Composable da tela de detalhes
@Composable
fun PoesiaDetalhesScreen(
    navController: NavController,
    viewModel: PoesiaDetalhesViewModel = hiltViewModel()
) {
    // Coletamos a poesia selecionada do StateFlow do ViewModel
    // Usar collectAsStateWithLifecycle é a prática recomendada para segurança de ciclo de vida
    val poesia by viewModel.poesiaSelecionada.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Exibe um indicador de progresso enquanto a poesia está sendo carregada
        if (poesia == null) {
            CircularProgressIndicator()
        } else {
            // Quando a poesia estiver disponível, exibe o conteúdo detalhado
            PoesiaDetailContent(
                poesia = poesia!!,
                // Passa a função de evento do ViewModel para o conteúdo
                onPoesiaEvent = viewModel::onPoesiaEvent,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PoesiaDetailContent(
    poesia: Poesia,
    onPoesiaEvent: (Poesia) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Adiciona um botão de voltar
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        // Título da Poesia
        Text(
            text = poesia.titulo,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Texto completo da Poesia
        Text(
            text = poesia.texto,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Barra de Ações (Favoritar)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botão de Favorito
            IconButton(
                onClick = {
                    val poesiaAtualizada = poesia.copy(
                        isFavorito = !poesia.isFavorito,
                        dataFavoritado = if (!poesia.isFavorito) System.currentTimeMillis() else null
                    )
                    onPoesiaEvent(poesiaAtualizada)
                }
            ) {
                Icon(
                    imageVector = if (poesia.isFavorito) Icones.Favorito else Icones.DesmarcaFavorito,
                    contentDescription = "Favoritar",
                    tint = if (poesia.isFavorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
