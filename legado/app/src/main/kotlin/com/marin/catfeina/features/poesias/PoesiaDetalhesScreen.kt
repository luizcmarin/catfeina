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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.formatador.TextoFormatado
import com.marin.catfeina.core.formatador.parser.ParserTextoFormatado
import com.marin.catfeina.core.tts.TtsStatus
import com.marin.catfeina.features.marcador.MarcadorBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoesiaDetalhesScreen(
    navController: NavController,
    viewModel: PoesiaDetalhesViewModel = hiltViewModel()
) {
    val poesia by viewModel.poesiaSelecionada.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val ttsStatus by viewModel.ttsStatus.collectAsStateWithLifecycle()
    val marcadores by viewModel.marcadores.collectAsStateWithLifecycle()
    val parser = viewModel.getParser()

    val bottomSheetState = rememberModalBottomSheetState()

    if (uiState.isMarcadorSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onUiEvent(PoesiaDetalhesEvent.ToggleMarcadorSheet(false)) },
            sheetState = bottomSheetState
        ) {
            MarcadorBottomSheetContent(
                marcadores = marcadores,
                onSlotSelected = { slotId ->
                    viewModel.onUiEvent(PoesiaDetalhesEvent.OnMarcadorClick(slotId))
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (poesia == null) {
            CircularProgressIndicator()
        } else {
            PoesiaDetailContent(
                poesia = poesia!!,
                uiState = uiState,
                ttsStatus = ttsStatus,
                parser = parser,
                onUiEvent = viewModel::onUiEvent,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PoesiaDetailContent(
    poesia: Poesia,
    uiState: PoesiaDetalhesUiState,
    ttsStatus: TtsStatus,
    parser: ParserTextoFormatado,
    onUiEvent: (PoesiaDetalhesEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(key1 = poesia.id) {
        if (!poesia.isLido) {
            onUiEvent(PoesiaDetalhesEvent.MarcarComoLido)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
            }
            ControlesAcoes(poesia = poesia, uiState = uiState, ttsStatus = ttsStatus, onEvent = onUiEvent)
        }

        poesia.imagem?.let {
            AsyncImage(
                model = it,
                contentDescription = "Imagem da poesia: ${poesia.titulo}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = poesia.titulo,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            TextoFormatado(textoCru = poesia.texto, parser = parser, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(32.dp))

            AnotacaoUsuario(uiState, onUiEvent)
        }
    }
}

@Composable
private fun AnotacaoUsuario(
    uiState: PoesiaDetalhesUiState,
    onUiEvent: (PoesiaDetalhesEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Sua Anotação", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.textoAnotacao,
            onValueChange = { onUiEvent(PoesiaDetalhesEvent.AnotacaoChanged(it)) },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text("Escreva seus pensamentos...") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onUiEvent(PoesiaDetalhesEvent.SalvarAnotacao) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Salvar Anotação")
        }
    }
}

@Composable
private fun ControlesAcoes(
    poesia: Poesia,
    uiState: PoesiaDetalhesUiState,
    ttsStatus: TtsStatus,
    onEvent: (PoesiaDetalhesEvent) -> Unit
) {
    Row {
        IconButton(onClick = { onEvent(PoesiaDetalhesEvent.TocarOuPausarTTS) }) {
            val iconePlayPause = when (ttsStatus) {
                TtsStatus.SPEAKING -> Icones.PausarTTS
                else -> Icones.TocarTTS
            }
            Icon(imageVector = iconePlayPause, contentDescription = "Tocar ou Pausar Leitura")
        }

        if (ttsStatus == TtsStatus.SPEAKING || ttsStatus == TtsStatus.PAUSED) {
            IconButton(onClick = { onEvent(PoesiaDetalhesEvent.PararTTS) }) {
                Icon(imageVector = Icones.PararTTS, contentDescription = "Parar Leitura")
            }
        }

        IconButton(onClick = { onEvent(PoesiaDetalhesEvent.TocarOuPausarSomAmbiente) }) {
            Icon(
                imageVector = Icones.SomAmbiente,
                contentDescription = "Tocar Som Ambiente",
                tint = if (uiState.isSomAmbienteTocando) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(
            onClick = {
                val poesiaAtualizada = poesia.copy(
                    isFavorito = !poesia.isFavorito,
                    dataFavoritado = if (!poesia.isFavorito) System.currentTimeMillis() else null
                )
                onEvent(PoesiaDetalhesEvent.SalvarPoesia(poesiaAtualizada, isFavoritoToggle = true))
            }
        ) {
            Icon(
                imageVector = if (poesia.isFavorito) Icones.FavoritoCheio else Icones.FavoritoVazio,
                contentDescription = "Favoritar",
                tint = if (poesia.isFavorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        IconButton(onClick = { onEvent(PoesiaDetalhesEvent.ToggleMarcadorSheet(true)) }) {
            Icon(
                imageVector = Icones.MarcadorVazio,
                contentDescription = "Salvar Atalho"
            )
        }
    }
}

fun PoesiaDetalhesViewModel.getParser(): ParserTextoFormatado {
    return this.parserTexto
}
