// =============================================================================
// Arquivo: com.marin.catfeina.core.data.entities.Marcador.kt
// Descrição: Entidade do Room para a tabela de marcadores (atalhos globais).
// =============================================================================
package com.marin.catfeina.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa um "Marcador" ou "Ponto de Retorno Salvo" na tabela 'marcadores'.
 * O sistema terá 10 slots fixos, identificados pelo slotId de 1 a 10.
 */
@Entity(tableName = "marcadores")
data class Marcador(
    /**
     * A chave primária, representando o slot fixo de 1 a 10.
     */
    @PrimaryKey
    val slotId: Int,

    /**
     * O texto a ser exibido para o usuário, descrevendo o atalho.
     * Pode ser nulo se o slot estiver vazio.
     */
    val tituloDisplay: String?,

    /**
     * A rota de navegação completa para a tela salva.
     * Ex: "poesia_detalhe/123"
     * Pode ser nulo se o slot estiver vazio.
     */
    val rotaNavegacao: String?,

    /**
     * O timestamp (em milissegundos) de quando o marcador foi salvo.
     * Pode ser nulo se o slot estiver vazio.
     */
    val dataSalvo: Long?
)
