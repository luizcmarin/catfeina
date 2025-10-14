// =============================================================================
// Arquivo: com.marin.catfeina.features.atelier.AtelierEditScreen.kt
// Descrição: Tela para criar ou editar uma nota do Atelier.
// =============================================================================
package com.marin.catfeina.features.atelier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.marin.catfeina.core.Icones

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtelierEditScreen(
    navController: NavController,
    viewModel: AtelierEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var shouldNavigateBack by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.titulo.isEmpty()) "Nova Nota" else "Editar Nota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icones.Voltar, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        viewModel.onEvent(AtelierEditEvent.Salvar)
                        shouldNavigateBack = true 
                    }) {
                        Icon(Icones.Check, contentDescription = "Salvar")
                    }
                    IconButton(onClick = { 
                        viewModel.onEvent(AtelierEditEvent.Deletar)
                        shouldNavigateBack = true 
                    }) {
                        Icon(Icones.Deletar, contentDescription = "Deletar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.titulo,
                onValueChange = { viewModel.onEvent(AtelierEditEvent.OnTituloChanged(it)) },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.conteudo,
                onValueChange = { viewModel.onEvent(AtelierEditEvent.OnConteudoChanged(it)) },
                label = { Text("Conteúdo") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }

    LaunchedEffect(shouldNavigateBack) {
        if (shouldNavigateBack) {
            navController.popBackStack()
        }
    }
}
