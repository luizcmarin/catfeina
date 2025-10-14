package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.AtelierEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO para interagir com a tabela 'atelier' no banco de dados.
 */
@Dao
interface AtelierDao {

    @Upsert
    suspend fun upsertAll(itens: List<AtelierEntity>)

    @Upsert
    suspend fun upsert(item: AtelierEntity)

    @Delete
    suspend fun delete(item: AtelierEntity)

    @Query("SELECT * FROM atelier ORDER BY dataAtualizacao DESC")
    fun getAll(): Flow<List<AtelierEntity>>

    @Query("SELECT * FROM atelier WHERE id = :id")
    fun getById(id: Long): Flow<AtelierEntity?>
}
