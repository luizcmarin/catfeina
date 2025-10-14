// =============================================================================
// Arquivo: com.marin.catfeina.core.data.daos.HistoricoVisitaDao.kt
// Descrição: DAO para a entidade de histórico de visitas.
// =============================================================================
package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.HistoricoVisita
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoricoVisitaDao {

    /**
     * Insere ou atualiza um item no histórico de visitas. Se um item com a mesma
     * chave primária já existir, ele será substituído.
     *
     * @param historicoVisita O item de histórico a ser salvo.
     */
    @Upsert
    suspend fun upsert(historicoVisita: HistoricoVisita)

    /**
     * Retorna um fluxo com todos os itens do histórico de visitas, ordenados
     * pela data de visita mais recente.
     *
     * @return Um Flow que emite a lista de todos os itens de histórico.
     */
    @Query("SELECT * FROM historico_visitas ORDER BY dataVisita DESC")
    fun getHistoricoCompleto(): Flow<List<HistoricoVisita>>

    /**
     * Remove todos os registros da tabela de histórico de visitas.
     */
    @Query("DELETE FROM historico_visitas")
    suspend fun limparHistorico()
}
