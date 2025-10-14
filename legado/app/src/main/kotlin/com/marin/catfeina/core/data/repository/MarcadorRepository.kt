// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.MarcadorRepository.kt
// Descrição: Interface que define o contrato para o repositório de marcadores.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.entities.Marcador
import kotlinx.coroutines.flow.Flow

interface MarcadorRepository {

    /**
     * Retorna um fluxo com a lista completa de todos os marcadores (slots).
     */
    fun getTodosOsMarcadores(): Flow<List<Marcador>>

    /**
     * Salva ou atualiza as informações de um marcador em um slot específico.
     *
     * @param slotId O ID do slot (1 a 10) a ser salvo.
     * @param titulo O texto de exibição para o marcador.
     * @param rota A rota de navegação completa a ser salva.
     */
    suspend fun salvarMarcador(slotId: Int, titulo: String, rota: String)

    /**
     * Limpa as informações de um marcador de um slot específico.
     *
     * @param slotId O ID do slot (1 a 10) a ser limpo.
     */
    suspend fun limparMarcador(slotId: Int)

}
