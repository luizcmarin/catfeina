// =============================================================================
// Arquivo: com.marin.catfeina.core.data.daos.MarcadorDao.kt
// Descrição: DAO para a entidade de marcadores (atalhos globais).
// =============================================================================
package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.Marcador
import kotlinx.coroutines.flow.Flow

@Dao
interface MarcadorDao {

    /**
     * Salva ou atualiza um marcador. Como o slotId é a chave primária, um marcador
     * com o mesmo slotId será substituído.
     */
    @Upsert
    suspend fun upsert(marcador: Marcador)

    /**
     * Retorna um fluxo com todos os marcadores salvos, ordenados pelo ID do slot.
     */
    @Query("SELECT * FROM marcadores ORDER BY slotId ASC")
    fun getTodosOsMarcadores(): Flow<List<Marcador>>

    /**
     * Limpa um marcador específico, efetivamente esvaziando o slot.
     * Isso é feito atualizando os campos para null.
     */
    @Query("UPDATE marcadores SET tituloDisplay = null, rotaNavegacao = null, dataSalvo = null WHERE slotId = :slotId")
    suspend fun limparMarcador(slotId: Int)

    /**
     * Limpa todos os marcadores da tabela.
     */
    @Query("DELETE FROM marcadores")
    suspend fun limparTodosOsMarcadores()
}
