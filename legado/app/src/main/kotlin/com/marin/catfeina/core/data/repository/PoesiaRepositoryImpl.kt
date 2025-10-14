// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.PoesiaRepositoryImpl.kt
// Descrição: Implementação concreta do PoesiaRepository.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.PoesiaDao
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.entities.PoesiaEntity
import com.marin.catfeina.core.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PoesiaRepositoryImpl @Inject constructor(
    private val poesiaDao: PoesiaDao,
    private val remoteDataSource: RemoteDataSource
) : PoesiaRepository {

    override fun getPoesiasCompletas(): Flow<List<Poesia>> {
        return poesiaDao.getPoesiasCompletas()
    }

    override fun getPoesiaById(id: Long): Flow<Poesia?> {
        return poesiaDao.getPoesiaCompletaById(id)
    }

    override suspend fun salvarPoesia(poesia: Poesia) {
        poesiaDao.salvarPoesiaCompleta(poesia)
    }

    override fun getUltimaPoesiaAdicionada(): Flow<PoesiaEntity?> {
        return poesiaDao.getUltimaPoesiaAdicionada()
    }

    override fun getPoesiaAleatoria(): Flow<Poesia?> {
        return poesiaDao.getPoesiaAleatoria()
    }

    override fun getPoesiasFavoritas(): Flow<List<Poesia>> {
        return poesiaDao.getPoesiasFavoritas()
    }

    override suspend fun syncPoesias() {
        // Desativado para testes.
        // remoteDataSource.getConteudo().onSuccess {
        //     poesiaDao.upsertAll(it.poesias)
        // }.onFailure {
        //     // Tratar falha - por enquanto, apenas logamos no Logcat (feito no RemoteDataSource)
        // }
    }
}
