// =============================================================================
// Arquivo: com.marin.catfeina.features.atelier.AtelierEditViewModel.kt
// Descrição: ViewModel para a tela de edição do Atelier.
// =============================================================================
package com.marin.catfeina.features.atelier

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.AtelierEntity
import com.marin.catfeina.core.data.repository.AtelierRepository
import com.marin.catfeina.core.di.CatshitoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ATELIER_ITEM_ID_ARG = "itemId"

@HiltViewModel
class AtelierEditViewModel @Inject constructor(
    private val atelierRepository: AtelierRepository,
    private val catshitoService: CatshitoService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Long? = savedStateHandle[ATELIER_ITEM_ID_ARG]
    private var isNovaNota = (itemId == null || itemId == 0L)

    private val _uiState = MutableStateFlow(AtelierEditUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (!isNovaNota) {
            atelierRepository.getItemById(itemId!!).onEach { item ->
                item?.let {
                    _uiState.update {
                        it.copy(
                            titulo = item.titulo,
                            conteudo = item.conteudo,
                            isFixada = item.fixada
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: AtelierEditEvent) {
        when (event) {
            is AtelierEditEvent.OnTituloChanged -> _uiState.update { it.copy(titulo = event.titulo) }
            is AtelierEditEvent.OnConteudoChanged -> _uiState.update { it.copy(conteudo = event.conteudo) }
            is AtelierEditEvent.OnFixadaChanged -> _uiState.update { it.copy(isFixada = event.isFixada) }
            AtelierEditEvent.Salvar -> salvar()
            AtelierEditEvent.Deletar -> deletar()
        }
    }

    private fun salvar() {
        viewModelScope.launch {
            val item = AtelierEntity(
                id = itemId ?: 0,
                titulo = uiState.value.titulo,
                conteudo = uiState.value.conteudo,
                fixada = uiState.value.isFixada,
                dataAtualizacao = System.currentTimeMillis()
            )
            atelierRepository.salvarItem(item)

            // Se for uma nota nova, verifica a conquista
            if (isNovaNota) {
                //catshitoService.verificarConquistasDeAtelier() TODO: Método inexistente
                isNovaNota = false // Evita contar a mesma nota várias vezes
            }
        }
    }

    private fun deletar() {
        viewModelScope.launch {
            if (itemId != null) {
                val item = AtelierEntity(
                    id = itemId,
                    titulo = uiState.value.titulo,
                    conteudo = uiState.value.conteudo,
                    fixada = uiState.value.isFixada,
                    dataAtualizacao = System.currentTimeMillis()
                )
                atelierRepository.deletarItem(item)
            }
        }
    }
}

data class AtelierEditUiState(
    val titulo: String = "",
    val conteudo: String = "",
    val isFixada: Boolean = false
)

sealed interface AtelierEditEvent {
    data class OnTituloChanged(val titulo: String) : AtelierEditEvent
    data class OnConteudoChanged(val conteudo: String) : AtelierEditEvent
    data class OnFixadaChanged(val isFixada: Boolean) : AtelierEditEvent
    object Salvar : AtelierEditEvent
    object Deletar : AtelierEditEvent
}
