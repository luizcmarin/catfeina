// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.MarcadorRepositoryImpl.kt
// Descrição: Implementação do repositório de marcadores (atalhos globais).
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.MarcadorDao
import com.marin.catfeina.core.data.entities.Marcador
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarcadorRepositoryImpl @Inject constructor(
    private val marcadorDao: MarcadorDao
) : MarcadorRepository {

    override fun getTodosOsMarcadores(): Flow<List<Marcador>> {
        return marcadorDao.getTodosOsMarcadores()
    }

    override suspend fun salvarMarcador(slotId: Int, titulo: String, rota: String) {
        val marcador = Marcador(
            slotId = slotId,
            tituloDisplay = titulo,
            rotaNavegacao = rota,
            dataSalvo = System.currentTimeMillis()
        )
        marcadorDao.upsert(marcador)
    }

    override suspend fun limparMarcador(slotId: Int) {
        marcadorDao.limparMarcador(slotId)
    }
}
