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

    /**
     * Retorna um fluxo com a lista de todos os itens do atelier.
     *
     * @return Um Flow que emite a lista de AtelierEntity.
     */
    fun getAllItens(): Flow<List<AtelierEntity>>

    /**
     * Retorna um fluxo com um item específico do atelier, buscado pelo seu ID.
     *
     * @param id O ID do item.
     * @return Um Flow que emite o AtelierEntity correspondente ou null se não encontrado.
     */
    fun getItemById(id: Long): Flow<AtelierEntity?>
}

