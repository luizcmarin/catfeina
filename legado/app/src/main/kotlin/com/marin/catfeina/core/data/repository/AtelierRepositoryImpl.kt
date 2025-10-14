// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.AtelierRepositoryImpl.kt
// Descrição: Implementação concreta do AtelierRepository.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.AtelierDao
import com.marin.catfeina.core.data.entities.AtelierEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementação do AtelierRepository que utiliza o AtelierDao como fonte de dados.
 *
 * @property atelierDao O Data Access Object para o atelier, injetado pelo Hilt.
 */
class AtelierRepositoryImpl @Inject constructor(
    private val atelierDao: AtelierDao
) : AtelierRepository {

    override fun getAllItens(): Flow<List<AtelierEntity>> {
        return atelierDao.getAll()
    }

    override fun getItemById(id: Long): Flow<AtelierEntity?> {
        return atelierDao.getById(id)
    }

    override suspend fun salvarItem(item: AtelierEntity) {
        atelierDao.upsert(item)
    }

    override suspend fun deletarItem(item: AtelierEntity) {
        atelierDao.delete(item)
    }
}
