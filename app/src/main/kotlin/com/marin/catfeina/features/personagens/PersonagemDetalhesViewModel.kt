// =============================================================================
// Arquivo: com.marin.catfeina.features.personagens.PersonagemDetalhesViewModel.kt
// Descrição: ViewModel para a tela de detalhes de um Personagem.
// =============================================================================
package com.marin.catfeina.features.personagens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.AppDestinationsArgs
import com.marin.catfeina.core.data.entities.PersonagemEntity
import com.marin.catfeina.core.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PersonagemDetalhesViewModel @Inject constructor(
    private val personagemRepository: PersonagemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Argumento de navegação para o ID do personagem
    private val personagemId: StateFlow<Long> =
        savedStateHandle.getStateFlow(AppDestinationsArgs.PERSONAGEM_ID_ARG, 0L)

    // Busca o personagem correspondente ao ID
    val personagemSelecionado: StateFlow<PersonagemEntity?> = personagemId.flatMapLatest { id ->
        if (id > 0L) {
            personagemRepository.getPersonagemById(id)
        } else {
            flowOf(null)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}
