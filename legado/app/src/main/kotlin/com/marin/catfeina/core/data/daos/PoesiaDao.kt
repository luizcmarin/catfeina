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

    @Upsert
    suspend fun upsertPoesia(poesia: PoesiaEntity)

    @Upsert
    suspend fun upsertAll(poesias: List<PoesiaEntity>)

    @Upsert
    suspend fun upsertNota(nota: PoesiaNotaEntity)

    @Transaction
    @Query("""
        SELECT
            p.id, p.categoria, p.titulo, p.texto, p.imagem, p.autor, p.nota, 
            p.textoBase AS textoBase, p.textoFinal AS textoFinal, p.link AS campoUrl, 
            p.dataCriacao AS dataCriacao, COALESCE(pn.ehFavorita, 0) AS isFavorito, 
            pn.dataFavoritado AS dataFavoritado, COALESCE(pn.foiLida, 0) AS isLido, 
            pn.dataLeitura AS dataLeitura, pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        LEFT JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        ORDER BY p.dataCriacao DESC
    """)
    fun getPoesiasCompletas(): Flow<List<Poesia>>

    @Transaction
    @Query("""
        SELECT
            p.id, p.categoria, p.titulo, p.texto, p.imagem, p.autor, p.nota, 
            p.textoBase AS textoBase, p.textoFinal AS textoFinal, p.link AS campoUrl, 
            p.dataCriacao AS dataCriacao, COALESCE(pn.ehFavorita, 0) AS isFavorito, 
            pn.dataFavoritado AS dataFavoritado, COALESCE(pn.foiLida, 0) AS isLido, 
            pn.dataLeitura AS dataLeitura, pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        LEFT JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        WHERE p.id = :id
    """)
    fun getPoesiaCompletaById(id: Long): Flow<Poesia?>

    @Transaction
    @Query("""
        SELECT
            p.id, p.categoria, p.titulo, p.texto, p.imagem, p.autor, p.nota, 
            p.textoBase AS textoBase, p.textoFinal AS textoFinal, p.link AS campoUrl, 
            p.dataCriacao AS dataCriacao, COALESCE(pn.ehFavorita, 0) AS isFavorito, 
            pn.dataFavoritado AS dataFavoritado, COALESCE(pn.foiLida, 0) AS isLido, 
            pn.dataLeitura AS dataLeitura, pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        LEFT JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        ORDER BY RANDOM() LIMIT 1
    """)
    fun getPoesiaAleatoria(): Flow<Poesia?>

    @Transaction
    @Query("""
        SELECT
            p.id, p.categoria, p.titulo, p.texto, p.imagem, p.autor, p.nota, 
            p.textoBase AS textoBase, p.textoFinal AS textoFinal, p.link AS campoUrl, 
            p.dataCriacao AS dataCriacao, COALESCE(pn.ehFavorita, 0) AS isFavorito, 
            pn.dataFavoritado AS dataFavoritado, COALESCE(pn.foiLida, 0) AS isLido, 
            pn.dataLeitura AS dataLeitura, pn.notaUsuario AS notaUsuario
        FROM poesias AS p
        INNER JOIN PoesiaNota AS pn ON p.id = pn.poesiaId
        WHERE pn.ehFavorita = 1
        ORDER BY pn.dataFavoritado DESC
    """)
    fun getPoesiasFavoritas(): Flow<List<Poesia>>

    @Transaction
    suspend fun salvarPoesiaCompleta(poesia: Poesia) {
        upsertPoesia(poesia.toPoesiaEntity())
        upsertNota(poesia.toPoesiaNotaEntity())
    }

    @Query("SELECT * FROM poesias ORDER BY dataCriacao DESC LIMIT 1")
    fun getUltimaPoesiaAdicionada(): Flow<PoesiaEntity?>

    @Query("SELECT COUNT(id) FROM poesias")
    suspend fun count(): Int

}
