// =============================================================================
// Arquivo: com.marin.catfeina.core.data.entities.InformativoEntity.kt
// Descrição: Entidade do Room para a tabela de textos gerais e seu modelo de domínio.
// =============================================================================
package com.marin.catfeina.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade do Room que representa um conteúdo de texto genérico na tabela 'informativos'.
 * Útil para armazenar textos estáticos como "Sobre", "Ajuda", etc.
 *
 * @property chave A chave única que identifica o texto (ex: "SOBRE_APP"). Chave primária.
 * @property conteudo O conteúdo de texto associado à chave.
 * @property dataAtualizacao Timestamp da última atualização do registro.
 */
@Entity(tableName = "informativos")
data class InformativoEntity(
    @PrimaryKey
    val chave: String,

    val conteudo: String,

    @ColumnInfo(name = "dataAtualizacao")
    val dataAtualizacao: Long
)

/**
 * Modelo de domínio que representa um texto geral na lógica de negócios da aplicação.
 * Utilizado nas camadas de UI e ViewModel.
 */
data class Informativo(
    val chave: String,
    val conteudo: String
)

/**
 * Converte a entidade InformativoEntity (Room) para o modelo de domínio Informativo.
 *
 * @return O objeto de domínio Informativo correspondente.
 */
fun InformativoEntity.toDomain(): Informativo {
    return Informativo(
        chave = this.chave,
        conteudo = this.conteudo
    )
}

/**
 * Converte o modelo de domínio Informativo para a entidade InformativoEntity (Room).
 *
 * @return O objeto de entidade InformativoEntity correspondente.
 */
fun Informativo.toEntity(): InformativoEntity {
    return InformativoEntity(
        chave = this.chave,
        conteudo = this.conteudo,
        dataAtualizacao = System.currentTimeMillis()
    )
}

