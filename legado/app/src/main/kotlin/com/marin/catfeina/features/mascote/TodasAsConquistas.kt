// =============================================================================
// Arquivo: com.marin.catfeina.features.mascote.TodasAsConquistas.kt
// Descrição: Fonte de dados estática para todas as conquistas do jogo.
// =============================================================================
package com.marin.catfeina.features.mascote

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.WorkspacePremium

/**
 * Objeto que serve como uma fonte de dados central e estática para
 * todas as conquistas disponíveis no aplicativo.
 */
object TodasAsConquistas {
    val lista: List<Conquista> = listOf(
        Conquista(
            id = "primeira_leitura",
            titulo = "Página Virada",
            descricao = "Leia sua primeira poesia.",
            icone = Icons.AutoMirrored.Filled.MenuBook
        ),
        Conquista(
            id = "dez_poesias",
            titulo = "Leitor Voraz",
            descricao = "Leia 10 poesias diferentes.",
            icone = Icons.Default.AutoStories
        ),
        Conquista(
            id = "poesia_favorita",
            titulo = "Coração Marcado",
            descricao = "Favorite sua primeira poesia.",
            icone = Icons.Default.Favorite
        ),
        Conquista(
            id = "cinco_favoritas",
            titulo = "Colecionador de Emoções",
            descricao = "Favorite 5 poesias.",
            icone = Icons.Default.Star
        ),
        Conquista(
            id = "primeiro_atalho",
            titulo = "Marcador de Página",
            descricao = "Crie seu primeiro atalho para uma poesia.",
            icone = Icons.Default.Bookmark
        ),
        Conquista(
            id = "nivel_jovem",
            titulo = "Um Novo Amigo",
            descricao = "Acompanhe a evolução do Catshito para o nível Jovem.",
            icone = Icons.Default.SelfImprovement
        ),
        Conquista(
            id = "nivel_explorador",
            titulo = "Pequeno Explorador",
            descricao = "Ajude Catshito a se tornar um Explorador.",
            icone = Icons.Default.TravelExplore
        ),
        Conquista(
            id = "nivel_mestre",
            titulo = "Mestre dos Versos",
            descricao = "Alcance o nível Mestre com Catshito.",
            icone = Icons.Default.Lightbulb
        ),
        Conquista(
            id = "nivel_lendario",
            titulo = "Lenda Felina",
            descricao = "Transforme Catshito em uma Lenda.",
            icone = Icons.Default.Grade
        ),
        Conquista(
            id = "todas_conquistas",
            titulo = "Caçador de Conquistas",
            descricao = "Desbloqueie todas as outras conquistas.",
            icone = Icons.Default.WorkspacePremium
        )
    )
}
