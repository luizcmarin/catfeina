// =============================================================================
// Arquivo: com.marin.catfeina.core.data.entities.HistoricoVisita.kt
// Descrição: Entidade do Room para a tabela de histórico de visitas.
// =============================================================================
package com.marin.catfeina.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Enum para representar os diferentes tipos de conteúdo que podem ser visitados.
 */
enum class TipoConteudo {
    POESIA,
    PERSONAGEM,
    INFORMATIVO
}

/**
 * Entidade do Room que representa um registro na tabela de histórico de visitas.
 */
@Entity(
    tableName = "historico_visitas",
    indices = [
        Index(value = ["dataVisita"], unique = false),
        Index(value = ["tipoConteudo", "conteudoId"], unique = false)
    ]
)
data class HistoricoVisita(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "tipoConteudo")
    val tipoConteudo: TipoConteudo,

    @ColumnInfo(name = "conteudoId")
    val conteudoId: Long,

    @ColumnInfo(name = "tituloDisplay")
    val tituloDisplay: String,

    @ColumnInfo(name = "imagemDisplay")
    val imagemDisplay: String? = null,

    @ColumnInfo(name = "dataVisita")
    val dataVisita: Long
)
