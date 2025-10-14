// =============================================================================
// Arquivo: com.marin.catfeina.core.data.entities.AtelierEntity.kt
// Descrição: Entidade do Room para a tabela de notas do atelier e seu modelo de domínio.
// =============================================================================
package com.marin.catfeina.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "atelier",
    indices = [
        Index(value = ["dataAtualizacao"]),
        Index(value = ["fixada"])
    ]
)
data class AtelierEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val titulo: String,
    val conteudo: String,

    @ColumnInfo(name = "dataAtualizacao")
    val dataAtualizacao: Long,

    @ColumnInfo(defaultValue = "0")
    val fixada: Boolean
)

/**
 * Modelo de domínio que representa uma nota do atelier na lógica de negócios da aplicação.
 */
data class AtelierNota(
    val id: Long,
    val titulo: String,
    val conteudo: String,
    val dataAtualizacao: Long,
    val isFixada: Boolean
)

/**
 * Converte a entidade AtelierEntity (Room) para o modelo de domínio AtelierNota.
 *
 * @return O objeto de domínio AtelierNota correspondente.
 */
fun AtelierEntity.toDomain(): AtelierNota {
    return AtelierNota(
        id = this.id,
        titulo = this.titulo,
        conteudo = this.conteudo,
        dataAtualizacao = this.dataAtualizacao,
        isFixada = this.fixada
    )
}

/**
 * Converte o modelo de domínio AtelierNota para a entidade AtelierEntity (Room).
 *
 * @return O objeto de entidade AtelierEntity correspondente.
 */
fun AtelierNota.toEntity(): AtelierEntity {
    return AtelierEntity(
        id = this.id,
        titulo = this.titulo,
        conteudo = this.conteudo,
        dataAtualizacao = System.currentTimeMillis(), // Sempre atualiza o timestamp ao salvar
        fixada = this.isFixada
    )
}

