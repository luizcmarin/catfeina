// =============================================================================
// Arquivo: com.marin.catfeina.core.data.entities.PoesiaEntity.kt
// Descrição: Entidade do Room para a tabela de poesias e seu modelo de domínio.
// =============================================================================
package com.marin.catfeina.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marin.catfeina.core.CategoriaPoesiaEnum

/**
 * Entidade do Room que representa os dados imutáveis de uma poesia na tabela 'poesias'.
 * O estado do usuário (favorito, lido, nota) é armazenado em PoesiaNotaEntity.
 */
@Entity(
    tableName = "poesias",
    indices = [
        Index(value = ["categoria"]),
        Index(value = ["titulo"]),
        Index(value = ["dataCriacao"])
    ]
)
data class PoesiaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val categoria: String,
    val titulo: String,

    @ColumnInfo(name = "textoBase")
    val textoBase: String,

    val texto: String,

    @ColumnInfo(name = "textoFinal")
    val textoFinal: String?,

    @ColumnInfo(name = "imagem")
    val imagem: String? = null,

    @ColumnInfo(name = "autor")
    val autor: String? = null,

    @ColumnInfo(name = "nota")
    val nota: String? = null,

    @ColumnInfo(name = "link")
    val campoUrl: String? = null,

    @ColumnInfo(name = "dataCriacao")
    val dataCriacao: Long
)

/**
 * Modelo de domínio combinado que representa uma poesia com o estado de interação do usuário.
 * Este modelo é o que a UI e os ViewModels usarão. Ele será construído no Repositório
 * a partir de uma junção (JOIN) entre PoesiaEntity e PoesiaNotaEntity.
 */
data class Poesia(
    val id: Long,
    val categoria: CategoriaPoesiaEnum,
    val titulo: String,
    val textoBase: String,
    val texto: String,
    val textoFinal: String?,
    val imagem: String?,
    val autor: String?,
    val nota: String?,
    val campoUrl: String?,
    val dataCriacao: Long,
    // Campos provenientes da PoesiaNotaEntity
    val isFavorito: Boolean,
    val dataFavoritado: Long?,
    val isLido: Boolean,
    val dataLeitura: Long?,
    var notaUsuario: String?
)

/**
 * Converte o modelo de domínio Poesia para a entidade PoesiaEntity (Room).
 * ATENÇÃO: Esta função converte apenas a parte dos dados da poesia. As informações
 * de estado (favorito, lido, nota) devem ser salvas separadamente na PoesiaNotaEntity.
 *
 * @return O objeto de entidade PoesiaEntity correspondente.
 */
fun Poesia.toPoesiaEntity(): PoesiaEntity {
    return PoesiaEntity(
        id = this.id,
        categoria = this.categoria.name,
        titulo = this.titulo,
        textoBase = this.textoBase,
        texto = this.texto,
        textoFinal = this.textoFinal,
        imagem = this.imagem,
        autor = this.autor,
        nota = this.nota,
        campoUrl = this.campoUrl,
        dataCriacao = this.dataCriacao
    )
}

/**
 * Converte o modelo de domínio Poesia para a entidade PoesiaNotaEntity (Room).
 * ATENÇÃO: Esta função converte apenas a parte dos dados de estado do usuário.
 *
 * @return O objeto de entidade PoesiaNotaEntity correspondente.
 */
fun Poesia.toPoesiaNotaEntity(): PoesiaNotaEntity {
    return PoesiaNotaEntity(
        poesiaId = this.id,
        ehFavorita = this.isFavorito,
        dataFavoritado = if (this.isFavorito) (this.dataFavoritado
            ?: System.currentTimeMillis()) else null,
        foiLida = this.isLido,
        dataLeitura = if (this.isLido) (this.dataLeitura ?: System.currentTimeMillis()) else null,
        notaUsuario = this.notaUsuario ?: ""
    )
}
