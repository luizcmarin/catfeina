// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.PersonagemRepositoryImpl.kt
// Descrição: Implementação concreta do PersonagemRepository.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.daos.PersonagemDao
import com.marin.catfeina.core.data.entities.PersonagemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementação do PersonagemRepository que utiliza o PersonagemDao como fonte de dados.
 *
 * @property personagemDao O Data Access Object para personagens, injetado pelo Hilt.
 */
class PersonagemRepositoryImpl @Inject constructor(
    private val personagemDao: PersonagemDao
) : PersonagemRepository {

    /**
     * Delega a busca de todos os personagens para o DAO.
     */
    override fun getAllPersonagens(): Flow<List<PersonagemEntity>> {
        return personagemDao.getAll()
    }

    /**
     * Delega a busca de um personagem específico por ID para o DAO.
     */
    override fun getPersonagemById(id: Long): Flow<PersonagemEntity?> {
        return personagemDao.getById(id)
    }
}
