// =============================================================================
// Arquivo: com.marin.catfeina.features.search.SearchRepositoryImpl.kt
// Descrição: Implementação do repositório de pesquisa global.
// =============================================================================
package com.marin.catfeina.features.search

import com.marin.catfeina.AppDestinations
import com.marin.catfeina.core.data.daos.SearchDao
import com.marin.catfeina.core.data.entities.TipoConteudo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDao: SearchDao
) : SearchRepository {

    override fun search(query: String): Flow<List<SearchResult>> {
        val poesiasFlow = searchDao.searchPoesias(query)
        val personagensFlow = searchDao.searchPersonagens(query)

        return combine(poesiasFlow, personagensFlow) { poesias, personagens ->
            val resultadosPoesias = poesias.map {
                SearchResult(
                    id = it.id,
                    titulo = it.titulo,
                    subtitulo = it.textoBase,
                    imagem = it.imagem,
                    tipo = TipoConteudo.POESIA,
                    rotaNavegacao = "${AppDestinations.POESIA_DETALHES_ROUTE}/${it.id}"
                )
            }

            val resultadosPersonagens = personagens.map {
                SearchResult(
                    id = it.id,
                    titulo = it.nome,
                    subtitulo = it.descricao,
                    imagem = it.imagem,
                    tipo = TipoConteudo.PERSONAGEM,
                    rotaNavegacao = "${AppDestinations.PERSONAGEM_DETALHES_ROUTE}/${it.id}"
                )
            }

            (resultadosPoesias + resultadosPersonagens).sortedBy { it.titulo } 
        }
    }
}
