// =============================================================================
// Arquivo: com.marin.catfeina.core.data.daos.PoesiaDao.kt
// Descrição: DAO (Data Access Object) para as entidades PoesiaEntity e PoesiaNotaEntity.
// =============================================================================
package com.marin.catfeina.core.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.marin.catfeina.core.data.entities.Poesia
import com.marin.catfeina.core.data.entities.PoesiaEntity
import com.marin.catfeina.core.data.entities.PoesiaNotaEntity
import com.marin.catfeina.core.data.entities.toPoesiaEntity
import com.marin.catfeina.core.data.entities.toPoesiaNotaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PoesiaDao {

    /**
     * Insere ou atualiza uma PoesiaEntity.
     * Se o item já existe, ele é substituído.
     */
    @Upsert
    suspend fun upsertPoesia(poesia: PoesiaEntity)

    /**
     * Insere ou atualiza uma PoesiaNotaEntity.
     * Se o item já existe, ele é substituído.
     */
    @Upsert
    suspend fun upsertNota(nota: PoesiaNotaEntity)

    /**
     * Busca todas as poesias e seus respectivos estados de nota (lido/favorito) usando um LEFT JOIN.
     * O resultado é mapeado para o modelo de domínio `Poesia`.
     * Retorna um Flow, tornando a query reativa a mudanças no banco de dados.
     *
     * @return Um Flow contendo a lista de todas as poesias.
     */
    @Transaction
    @Query("""
        SELECT
            p.id,
            p.categoria,
            p.titulo,
            p.texto,
            p.imagem,
            p.autor,
            p.nota,
            p.textoBase AS textoBase,
            p.textoFinal AS textoFinal,
            p.link AS campoUrl,
            p.dataCriacao AS dataCriacao,
            COALESCE(pn.ehFavorita, 0) AS isFavorito,
            pn.dataFavoritado AS dataFavoritado,
            COALESCE(pn.foiLida, 0) AS isLido,
            pn.dataLeitura AS dataLeitura,
            pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        LEFT JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        ORDER BY p.dataCriacao DESC
    """)
    fun getPoesiasCompletas(): Flow<List<Poesia>>

    /**
     * Busca uma única poesia completa pelo seu ID.
     * A query é idêntica à de buscar todas, mas com uma cláusula WHERE.
     *
     * @param id O ID da poesia a ser buscada.
     * @return Um Flow que emite a Poesia ou null se não for encontrada.
     */
    @Transaction
    @Query("""
        SELECT
            p.id,
            p.categoria,
            p.titulo,
            p.texto,
            p.imagem,
            p.autor,
            p.nota,
            p.textoBase AS textoBase,
            p.textoFinal AS textoFinal,
            p.link AS campoUrl,
            p.dataCriacao AS dataCriacao,
            COALESCE(pn.ehFavorita, 0) AS isFavorito,
            pn.dataFavoritado AS dataFavoritado,
            COALESCE(pn.foiLida, 0) AS isLido,
            pn.dataLeitura AS dataLeitura,
            pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        LEFT JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        WHERE p.id = :id
    """)
    fun getPoesiaCompletaById(id: Long): Flow<Poesia?>

    /**
     * Salva uma poesia completa (dados e estado) de forma transacional.
     * Garante que ambas as operações (salvar poesia e salvar nota) sejam concluídas com sucesso.
     */
    @Transaction
    suspend fun salvarPoesiaCompleta(poesia: Poesia) {
        upsertPoesia(poesia.toPoesiaEntity())
        upsertNota(poesia.toPoesiaNotaEntity())
    }

    /**
     * Busca a poesia mais recente com base na data de criação.
     *
     * @return Um Flow que emite a PoesiaEntity mais recente ou null se a tabela estiver vazia.
     */
    @Query("SELECT * FROM poesias ORDER BY dataCriacao DESC LIMIT 1")
    fun getUltimaPoesiaAdicionada(): Flow<PoesiaEntity?>

    /**
     * Conta o número de poesias no banco.
     * Usado pelo DatabaseInitializer para verificar se o banco precisa ser populado.
     */
    @Query("SELECT COUNT(id) FROM poesias")
    suspend fun count(): Int

}
