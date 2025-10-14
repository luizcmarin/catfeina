// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.InformativoRepository.kt
// Descrição: Repositório para acessar os dados de textos informativos.
// =============================================================================
package com.marin.catfeina.core.data.repository

import com.marin.catfeina.core.data.entities.InformativoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface para o repositório que gerencia os dados de textos informativos.
 */
interface InformativoRepository {
    /**
     * Busca um texto informativo específico pela sua chave única.
     *
     * @param chave A chave do texto a ser buscado.
     * @return Um Flow que emite o InformativoEntity ou null.
     */
    fun getInformativoPorChave(chave: String): Flow<InformativoEntity?>
}
