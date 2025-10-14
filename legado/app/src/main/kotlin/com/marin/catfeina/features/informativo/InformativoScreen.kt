// =============================================================================
// Arquivo: com.marin.catfeina.features.informativo.InformativoScreen.kt
// Descrição: Tela que exibe o conteúdo formatado de um informativo.
// =============================================================================
package com.marin.catfeina.features.informativo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.marin.catfeina.core.formatador.RenderizarElementoConteudo
import com.marin.catfeina.core.formatador.Tooltip
import com.marin.catfeina.core.formatador.TooltipHandler

@Composable
fun InformativoScreen(
    navController: NavController,
    viewModel: InformativoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 1. Criando o estado para controlar a exibição e o texto do tooltip.
    var tooltipText by remember { mutableStateOf<String?>(null) }

    // 2. Criando o handler que usa a função mostrarTooltip para ATUALIZAR o estado.
    val tooltipHandler = remember {
        object : TooltipHandler {
            override fun mostrarTooltip(textoTooltip: String) {
                tooltipText = textoTooltip
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.elementosConteudo) { elemento ->
                        // 3. Passando o handler correto para o renderizador.
                        RenderizarElementoConteudo(
                            elemento = elemento,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            tooltipHandler = tooltipHandler
                        )
                    }
                }
            }

            // 4. Renderizando o Composable Tooltip somente quando o estado não for nulo.
            tooltipText?.let { texto ->
                Tooltip(texto = texto) {
                    // onDismissRequest: limpa o estado para esconder o tooltip.
                    tooltipText = null
                }
            }
        }
    }
}
