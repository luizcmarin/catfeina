// =============================================================================// Arquivo: com.marin.catfeina.core.data.repository.InformativoRepositoryImpl.kt
// Descrição: Implementação do repositório de dados informativos.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.InformativoDao
import com.marin.catfeina.core.data.entities.InformativoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InformativoRepositoryImpl @Inject constructor(
    private val informativoDao: InformativoDao
) : InformativoRepository {

    override fun getInformativoPorChave(chave: String): Flow<InformativoEntity?> {
        return informativoDao.getByChave(chave)
    }
}
    