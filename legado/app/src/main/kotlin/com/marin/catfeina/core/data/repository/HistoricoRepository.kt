// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.HistoricoRepository.kt
// Descrição: Interface que define o contrato para o repositório de histórico.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.entities.HistoricoVisita
import com.marin.catfeina.core.data.entities.TipoConteudo
import kotlinx.coroutines.flow.Flow

interface HistoricoRepository {

    /**
     * Adiciona um novo registro ao histórico de visitas.
     *
     * @param tipo O tipo do conteúdo visitado (Poesia, Personagem, etc.).
     * @param id O ID do conteúdo visitado.
     * @param titulo O título a ser exibido no histórico.
     * @param imagem A URL da imagem a ser exibida (opcional).
     */
    suspend fun adicionarVisita(tipo: TipoConteudo, id: Long, titulo: String, imagem: String?)

    /**
     * Retorna um fluxo com a lista completa de todos os itens do histórico, ordenados
     * do mais recente para o mais antigo.
     */
    fun getHistoricoCompleto(): Flow<List<HistoricoVisita>>

    /**
     * Apaga todos os registros do histórico de visitas.
     */
    suspend fun limparHistorico()
}
