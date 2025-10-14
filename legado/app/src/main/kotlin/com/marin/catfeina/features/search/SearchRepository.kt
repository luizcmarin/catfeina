// =============================================================================
// Arquivo: com.marin.catfeina.features.search.SearchRepository.kt
// Descrição: Interface para o repositório de pesquisa global.
// =============================================================================
package com.marin.catfeina.features.search

import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    /**
     * Realiza uma busca global em múltiplos tipos de conteúdo (poesias, personagens, etc.)
     *
     * @param query O termo de busca inserido pelo usuário.
     * @return Um Flow que emite uma lista unificada de [SearchResult].
     */
    fun search(query: String): Flow<List<SearchResult>>
}
