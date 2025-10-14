// =============================================================================
// Arquivo: com.marin.catfeina.features.personagens.PersonagemViewModel.kt
// Descrição: ViewModel para a feature de Personagens.
// =============================================================================
package com.marin.catfeina.features.personagens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.PersonagemEntity
import com.marin.catfeina.core.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel para a feature de Personagens.
 *
 * @param personagemRepository O repositório para acessar os dados dos personagens.
 *                             É injetado pelo Hilt.
 */
@HiltViewModel
class PersonagemViewModel @Inject constructor(
    private val personagemRepository: PersonagemRepository
) : ViewModel() {

    /**
     * Um StateFlow que expõe a lista de todos os personagens para a UI.
     * Os dados são coletados do repositório e mantidos enquanto o ViewModelScope
     * estiver ativo, sendo compartilhados entre os observadores.
     */
    val personagens: StateFlow<List<PersonagemEntity>> = personagemRepository.getAllPersonagens()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
