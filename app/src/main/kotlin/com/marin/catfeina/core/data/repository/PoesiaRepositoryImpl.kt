// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.PoesiaRepositoryImpl.kt
// Descrição: Implementação concreta do PoesiaRepository.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.PoesiaDao
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.entities.PoesiaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementação do PoesiaRepository que utiliza o PoesiaDao como fonte de dados.
 *
 * @property poesiaDao O Data Access Object para poesias, injetado pelo Hilt.
 */
class PoesiaRepositoryImpl @Inject constructor(
    private val poesiaDao: PoesiaDao
) : PoesiaRepository {

    /**
     * Delega a busca de poesias completas para o DAO.
     */
    override fun getPoesiasCompletas(): Flow<List<Poesia>> {
        return poesiaDao.getPoesiasCompletas()
    }

    /**
      * Delega a busca de uma poesia completa pelo seu ID para o DAO.
     */
    override fun getPoesiaById(id: Long): Flow<Poesia?> {
        return poesiaDao.getPoesiaCompletaById(id)
    }

    /**
     * Delega a operação de salvar uma poesia completa para o método transacional do DAO.
     */
    override suspend fun salvarPoesia(poesia: Poesia) {
        poesiaDao.salvarPoesiaCompleta(poesia)
    }

    override fun getUltimaPoesiaAdicionada(): Flow<PoesiaEntity?> {
        return poesiaDao.getUltimaPoesiaAdicionada()
    }
}
