// =============================================================================
// Arquivo: com.marin.catfeina.core.data.entities.PersonagemEntity.kt
// Descrição: Entidade do Room para a tabela de personagens e seu modelo de domínio.
// =============================================================================
package com.marin.catfeina.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidade do Room que representa um personagem na tabela 'personagens'.
 *
 * @property id O ID único do personagem (chave primária, autogerado).
 * @property nome O nome do personagem.
 * @property descricao Uma descrição detalhada sobre o personagem.
 * @property imagem A URL ou path para a imagem principal do personagem.
 * @property dataCriacao Timestamp (Long) da criação do registro.
 */
@Entity(
    tableName = "personagens",
    indices = [Index(value = ["nome"], unique = true)]
)
data class PersonagemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nome: String,
    val descricao: String,

    @ColumnInfo(name = "imagem_url")
    val imagem: String?,

    @ColumnInfo(name = "dataCriacao")
    val dataCriacao: Long
)

/**
 * Modelo de domínio que representa um personagem na lógica de negócios da aplicação.
 * Utilizado nas camadas de UI e ViewModel.
 */
data class Personagem(
    val id: Long,
    val nome: String,
    val descricao: String,
    val imagem: String?
)

/**
 * Converte a entidade PersonagemEntity (Room) para o modelo de domínio Personagem.
 *
 * @return O objeto de domínio Personagem correspondente.
 */
fun PersonagemEntity.toDomain(): Personagem {
    return Personagem(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        imagem = this.imagem
    )
}

/**
 * Converte o modelo de domínio Personagem para a entidade PersonagemEntity (Room).
 *
 * @return O objeto de entidade PersonagemEntity correspondente.
 */
fun Personagem.toEntity(): PersonagemEntity {
    return PersonagemEntity(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        imagem = this.imagem,
        dataCriacao = System.currentTimeMillis() // Gerenciado na criação/atualização
    )
}

