// =============================================================================
// Arquivo: com.marin.catfeina.core.data.daos.InformativoDao.kt
// Descrição: DAO (Data Access Object) para a entidade InformativoEntity.
// =============================================================================
package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.InformativoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO para interagir com a tabela 'informativos' no banco de dados.
 */
@Dao
interface InformativoDao {

    /**
     * Insere ou atualiza (se já existir) uma lista de textos gerais.
     * Útil para a carga inicial de dados.
     *
     * @param textos A lista de InformativoEntity a ser inserida/atualizada.
     */
    @Upsert
    suspend fun upsertAll(textos: List<InformativoEntity>)

    /**
     * Busca todos os textos gerais, ordenados pelo título.
     *
     * @return Um Flow que emite a lista de todos os InformativoEntity sempre que
     *         os dados da tabela mudam.
     */
    @Query("SELECT * FROM informativos ORDER BY chave ASC")
    fun getAll(): Flow<List<InformativoEntity>>

    /**
     * Busca um texto geral específico pela sua chave única.
     *
     * @param chave A chave única do texto a ser buscado.
     * @return Um Flow que emite o InformativoEntity correspondente ou null se não for encontrado.
     */
    @Query("SELECT * FROM informativos WHERE chave = :chave")
    fun getByChave(chave: String): Flow<InformativoEntity?>
}
