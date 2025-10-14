// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.PoesiaRepository.kt
// Descrição: Interface que define o contrato para o repositório de poesias.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.entities.PoesiaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface que define as operações de dados para a feature de poesias.
 * Abstrai a fonte de dados (Room, API, etc.) do resto da aplicação.
 */
interface PoesiaRepository {

    fun getPoesiasCompletas(): Flow<List<Poesia>>

    fun getPoesiaById(id: Long): Flow<Poesia?>

    suspend fun salvarPoesia(poesia: Poesia)

    fun getUltimaPoesiaAdicionada(): Flow<PoesiaEntity?>

    fun getPoesiaAleatoria(): Flow<Poesia?>

    fun getPoesiasFavoritas(): Flow<List<Poesia>>

    suspend fun syncPoesias()

}
