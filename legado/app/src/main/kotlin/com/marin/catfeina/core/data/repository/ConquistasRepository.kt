// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.ConquistasRepository.kt
// Descrição: Interface que define o contrato para o repositório de conquistas.
// =============================================================================
package com.marin.catfeina.core.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * Define as operações de dados para gerenciar as conquistas do usuário.
 */
interface ConquistasRepository {
    /**
     * Retorna um Flow com o conjunto de IDs das conquistas desbloqueadas.
     */
    fun getConquistasDesbloqueadas(): Flow<Set<String>>

    /**
     * Adiciona uma nova conquista ao conjunto de conquistas desbloqueadas.
     *
     * @param conquistaId O ID da conquista a ser adicionada.
     */
    suspend fun adicionarConquista(conquistaId: String)
}
