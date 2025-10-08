// =============================================================================
// Arquivo: com.marin.catfeina.features.atelier.AtelierViewModel.kt
// Descrição: ViewModel para a feature do Atelier.
// =============================================================================
package com.marin.catfeina.features.atelier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.entities.AtelierEntity
import com.marin.catfeina.core.data.repository.AtelierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel para a feature do Atelier.
 *
 * @param atelierRepository O repositório para acessar os dados dos itens do atelier.
 *                          É injetado pelo Hilt.
 */
@HiltViewModel
class AtelierViewModel @Inject constructor(
    private val atelierRepository: AtelierRepository
) : ViewModel() {

    /**
     * Um StateFlow que expõe a lista de todos os itens do atelier para a UI.
     * Os dados são coletados do repositório e mantidos enquanto o ViewModelScope
     * estiver ativo, sendo compartilhados entre os observadores.
     */
    val itens: StateFlow<List<AtelierEntity>> = atelierRepository.getAllItens()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
