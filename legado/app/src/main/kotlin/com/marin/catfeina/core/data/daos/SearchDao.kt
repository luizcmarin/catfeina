// =============================================================================
// Arquivo: com.marin.catfeina.core.data.daos.SearchDao.kt
// Descrição: DAO para a funcionalidade de pesquisa global.
// =============================================================================
package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.marin.catfeina.core.data.entities.PersonagemEntity
import com.marin.catfeina.core.data.entities.Poesia
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("""
        SELECT
            p.id, p.categoria, p.titulo, p.texto, p.imagem, p.autor, p.nota, 
            p.textoBase AS textoBase, p.textoFinal AS textoFinal, p.link AS campoUrl, 
            p.dataCriacao AS dataCriacao, COALESCE(pn.ehFavorita, 0) AS isFavorito, 
            pn.dataFavoritado AS dataFavoritado, COALESCE(pn.foiLida, 0) AS isLido, 
            pn.dataLeitura AS dataLeitura, pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        LEFT JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        WHERE p.titulo LIKE '%' || :query || '%' OR p.texto LIKE '%' || :query || '%'
    """)
    fun searchPoesias(query: String): Flow<List<Poesia>>

    @Query("SELECT * FROM personagens WHERE nome LIKE '%' || :query || '%' OR descricao LIKE '%' || :query || '%'")
    fun searchPersonagens(query: String): Flow<List<PersonagemEntity>>

}
