// ===================================================================================
// Arquivo: Globais.kt
// Descrição: Arquivo que centraliza constantes, enums e outros valores globais
//            utilizados em todo o código compartilhado do projeto.
// ===================================================================================

package com.marin.catfeina.core.utils

/**
 * Enum para categorizar os tipos de poesia.
 * Facilita a filtragem e a lógica de negócios sem usar strings "mágicas".
 */
enum class CategoriaPoesiaEnum {
    POESIA,
    CONTO,
    CRONICA,
    VERSO,
    PENSAMENTO,
    SONETO;
}

/**
 * Enum para representar os diferentes tipos de conteúdo que podem ser visitados no histórico.
 */
enum class TipoConteudo {
    POESIA,
    PERSONAGEM,
    INFORMATIVO
}
