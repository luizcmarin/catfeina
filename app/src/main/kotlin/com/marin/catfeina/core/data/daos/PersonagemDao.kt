// =============================================================================
// Arquivo: com.marin.catfeina.core.data.daos.PersonagemDao.kt
// Descrição: DAO (Data Access Object) para a entidade PersonagemEntity.
// =============================================================================
package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.PersonagemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO para interagir com a tabela 'personagens' no banco de dados.
 */
@Dao
interface PersonagemDao {

    /**
     * Insere ou atualiza (se já existir) uma lista de personagens.
     * Útil para a carga inicial de dados.
     *
     * @param personagens A lista de PersonagemEntity a ser inserida/atualizada.
     */
    @Upsert
    suspend fun upsertAll(personagens: List<PersonagemEntity>)

    /**
     * Busca todos os personagens do banco de dados, ordenados pelo nome.
     *
     * @return Um Flow que emite a lista de todos os PersonagemEntity sempre que
     *         os dados da tabela mudam.
     */
    @Query("SELECT * FROM personagens ORDER BY nome ASC")
    fun getAll(): Flow<List<PersonagemEntity>>

    /**
     * Busca um personagem específico pelo seu ID.
     *
     * @param id O ID do personagem a ser buscado.
     * @return Um Flow que emite o PersonagemEntity correspondente ou null se não for encontrado.
     */
    @Query("SELECT * FROM personagens WHERE id = :id")
    fun getById(id: Long): Flow<PersonagemEntity?>
}
