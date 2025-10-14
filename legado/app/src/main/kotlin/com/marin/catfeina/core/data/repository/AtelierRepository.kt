// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.AtelierRepository.kt
// Descrição: Interface que define o contrato para o repositório do atelier.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.entities.AtelierEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define as operações de dados para a feature do atelier.
 * Abstrai a fonte de dados do resto da aplicação.
 */
interface AtelierRepository {

    fun getAllItens(): Flow<List<AtelierEntity>>

    fun getItemById(id: Long): Flow<AtelierEntity?>

    suspend fun salvarItem(item: AtelierEntity)

    suspend fun deletarItem(item: AtelierEntity)
}
