// =============================================================================
// Arquivo: com.marin.catfeina.features.search.SearchResult.kt
// Descrição: Modelo de dados para um item de resultado de pesquisa unificado.
// =============================================================================
package com.marin.catfeina.features.search

import com.marin.catfeina.core.data.entities.TipoConteudo

/**
 * Representa um único item em uma lista de resultados de pesquisa, unificando
 * diferentes tipos de conteúdo (Poesia, Personagem, etc.) em um modelo comum.
 */
data class SearchResult(
    val id: Long,
    val titulo: String,
    val subtitulo: String,
    val imagem: String?,
    val tipo: TipoConteudo,
    val rotaNavegacao: String
)
