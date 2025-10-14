// =============================================================================
// Arquivo: com.marin.catfeina.features.informativo.InformativoViewModel.kt
// Descrição: ViewModel para a tela que exibe um texto informativo genérico.
// =============================================================================
package com.marin.catfeina.features.informativo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.repository.InformativoRepository
import com.marin.catfeina.core.formatador.ElementoConteudo
import com.marin.catfeina.core.formatador.parser.ParserTextoFormatado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Estado da UI para a tela de Informativo.
 * @param isLoading Indica se o conteúdo está sendo carregado.
 * @param elementosConteudo A lista de conteúdo formatado pronto para ser renderizado.
 */
data class InformativoUiState(
    val isLoading: Boolean = true,
    val elementosConteudo: List<ElementoConteudo> = emptyList()
)

@HiltViewModel
class InformativoViewModel @Inject constructor(
    informativoRepository: InformativoRepository,
    private val parser: ParserTextoFormatado, // Injeta o parser correto
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val informativoChave: String =
        savedStateHandle.get<String>("informativoChave").orEmpty()

    /**
     * Expõe um StateFlow que contém o estado da UI para a tela de informativo.
     *
     * Este fluxo observa o repositório para obter a entidade `InformativoEntity`.
     * Assim que a entidade é recebida, seu conteúdo em string é processado pelo `ParserTextoFormatado`,
     * transformando-o em uma lista de `ElementoConteudo`.
     * O resultado é emitido como parte do `InformativoUiState`.
     */
    val uiState: StateFlow<InformativoUiState> =
        informativoRepository.getInformativoPorChave(informativoChave)
            .map { informativo ->
                if (informativo != null && informativo.conteudo.isNotBlank()) {
                    // Se temos um informativo, parseamos seu conteúdo com o método correto
                    InformativoUiState(
                        isLoading = false,
                        elementosConteudo = parser.parse(informativo.conteudo)
                    )
                } else {
                    // Se não, o estado é "não carregando" com conteúdo vazio
                    InformativoUiState(isLoading = false, elementosConteudo = emptyList())
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                // O valor inicial reflete o estado de carregamento
                initialValue = InformativoUiState(isLoading = true)
            )
}
