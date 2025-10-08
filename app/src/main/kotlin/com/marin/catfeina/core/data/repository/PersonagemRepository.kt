// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.PersonagemRepository.kt
// Descrição: Interface que define o contrato para o repositório de personagens.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.entities.PersonagemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define as operações de dados para a feature de personagens.
 * Abstrai a fonte de dados do resto da aplicação.
 */
interface PersonagemRepository {

    /**
     * Retorna um fluxo com a lista de todos os personagens.
     *
     * @return Um Flow que emite a lista de PersonagemEntity.
     */
    fun getAllPersonagens(): Flow<List<PersonagemEntity>>

    /**
     * Retorna um fluxo com um personagem específico, buscado pelo seu ID.
     *
     * @param id O ID do personagem.
     * @return Um Flow que emite o PersonagemEntity correspondente ou null se não encontrado.
     */
    fun getPersonagemById(id: Long): Flow<PersonagemEntity?>
}

