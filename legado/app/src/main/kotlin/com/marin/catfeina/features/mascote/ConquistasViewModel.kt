// =============================================================================
// Arquivo: com.marin.catfeina.features.mascote.ConquistasViewModel.kt
// Descrição: ViewModel para a tela de Conquistas e Mascote.
// =============================================================================
package com.marin.catfeina.features.mascote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.catfeina.core.data.repository.ConquistasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * Define o estado da UI para a tela de Conquistas, contendo todas as
 * informações necessárias para renderizar a tela de forma consistente.
 */
data class ConquistasUiState(
    val nivelMascote: NivelMascote = NivelMascote.FILHOTE,
    val fraseMascote: String = "",
    val todasAsConquistas: List<Conquista> = emptyList(),
    val conquistasDesbloqueadas: Set<String> = emptySet(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ConquistasViewModel @Inject constructor(
    private val conquistasRepository: ConquistasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConquistasUiState())
    val uiState: StateFlow<ConquistasUiState> = _uiState.asStateFlow()

    init {
        carregarDadosIniciais()
    }

    private fun carregarDadosIniciais() {
        viewModelScope.launch {
            // Simulação de carregamento do nível do mascote (pode ser de outro repositório)
            val nivelAtual = NivelMascote.EXPLORADOR // Exemplo fixo

            // Combina o fluxo de conquistas desbloqueadas do repositório com os dados estáticos
            conquistasRepository.getConquistasDesbloqueadas().collect { desbloqueadas ->
                _uiState.update {
                    it.copy(
                        nivelMascote = nivelAtual,
                        todasAsConquistas = TodasAsConquistas.lista,
                        conquistasDesbloqueadas = desbloqueadas,
                        fraseMascote = it.fraseMascote.ifEmpty { sortearNovaFrase(null) },
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Sorteia uma nova frase da lista, garantindo que não seja a mesma que a anterior.
     * @param fraseAnterior A frase que está sendo exibida atualmente.
     * @return Uma nova frase aleatória.
     */
    private fun sortearNovaFrase(fraseAnterior: String?): String {
        val frasesDisponiveis = FrasesMascote.lista.filter { it != fraseAnterior }
        if (frasesDisponiveis.isEmpty()) {
            return FrasesMascote.lista.firstOrNull() ?: "Miau?"
        }
        return frasesDisponiveis[Random.nextInt(frasesDisponiveis.size)]
    }

    /**
     * Função pública para ser chamada pela UI para atualizar a frase do mascote.
     */
    fun atualizarFrase() {
        _uiState.update {
            val novaFrase = sortearNovaFrase(it.fraseMascote)
            it.copy(fraseMascote = novaFrase)
        }
    }
}
