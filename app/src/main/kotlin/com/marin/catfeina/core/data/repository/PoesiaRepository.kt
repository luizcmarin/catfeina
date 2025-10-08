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

    /**
     * Retorna um fluxo com a lista completa de todas as poesias, incluindo
     * o estado de interação do usuário (favorito, lido).
     *
     * @return Um Flow que emite a lista de Poesias.
     */
    fun getPoesiasCompletas(): Flow<List<Poesia>>

    /**
     * Busca uma única poesia completa pelo seu ID.
     *
     * @param id O ID da poesia a ser buscada.
     * @return Um Flow que emite a Poesia correspondente ou null se não for encontrada.
     */
    fun getPoesiaById(id: Long): Flow<Poesia?>

    /**
     * Salva ou atualiza uma poesia completa (dados da poesia e estado de interação).
     *
     * @param poesia O objeto de domínio Poesia a ser salvo.
     */
    suspend fun salvarPoesia(poesia: Poesia)

    /**
     * Busca a poesia mais recente adicionada ao banco de dados.
     *
     * @return Um Flow que emite a PoesiaEntity mais recente ou null.
     */
    fun getUltimaPoesiaAdicionada(): Flow<PoesiaEntity?>

}
