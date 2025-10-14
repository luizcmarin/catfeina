// =============================================================================
// Arquivo: com.marin.catfeina.features.mascote.Conquista.kt
// Descrição: Define o modelo de dados para uma conquista.
// =============================================================================
package com.marin.catfeina.features.mascote

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Representa uma única conquista que o usuário pode desbloquear.
 *
 * @param id O identificador único da conquista.
 * @param titulo O nome da conquista, visível para o usuário.
 * @param descricao Uma breve explicação sobre como desbloquear a conquista.
 * @param icone O ícone que representa visualmente a conquista.
 */
data class Conquista(
    val id: String,
    val titulo: String,
    val descricao: String,
    val icone: ImageVector
)
