package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.AtelierEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO para interagir com a tabela 'atelier' no banco de dados.
 */
@Dao
interface AtelierDao {

    /**
     * Insere ou atualiza (se já existir) uma lista de itens do atelier.
     * Útil para a carga inicial de dados.
     *
     * @param itens A lista de AtelierEntity a ser inserida/atualizada.
     */
    @Upsert
    suspend fun upsertAll(itens: List<AtelierEntity>)

    /**
     * Busca todos os itens do atelier, ordenados pela data de criação decrescente.
     *
     * @return Um Flow que emite a lista de todos os AtelierEntity sempre que
     *         os dados da tabela mudam.
     */
    @Query("SELECT * FROM atelier ORDER BY dataAtualizacao DESC")
    fun getAll(): Flow<List<AtelierEntity>>

    /**
     * Busca um item específico do atelier pelo seu ID.
     *
     * @param id O ID do item a ser buscado.
     * @return Um Flow que emite o AtelierEntity correspondente ou null se não for encontrado.
     */
    @Query("SELECT * FROM atelier WHERE id = :id")
    fun getById(id: Long): Flow<AtelierEntity?>
}