// =============================================================================
// Arquivo: com.marin.catfeina.core.di.CatshitoService.kt
// Descrição: Serviço central para gerenciar a lógica do mascote Catshito.
// =============================================================================
package com.marin.catfeina.core.di

import com.marin.catfeina.core.data.repository.ConquistasRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Serviço responsável por toda a lógica de negócios relacionada ao mascote Catshito.
 * Centraliza as regras de ganho de experiência, evolução e desbloqueio de conquistas.
 */
@Singleton
class CatshitoService @Inject constructor(
    private val conquistasRepository: ConquistasRepository
) {

    /**
     * Processa um evento de interação do usuário e aplica as regras de negócio.
     *
     * @param evento O tipo de evento que ocorreu.
     */
    suspend fun processarEvento(evento: EventoCatshito) {
        // Lógica futura para ganho de XP e evolução pode ser adicionada aqui.

        // Adiciona a conquista associada ao evento, se houver uma.
        evento.conquistaId?.let {
            conquistasRepository.adicionarConquista(it)
        }
    }
}

/**
 * Define os diferentes tipos de eventos que podem interagir com o Catshito
 * e potencialmente desbloquear conquistas.
 *
 * @param conquistaId O ID da conquista associada a este evento (opcional).
 */
enum class EventoCatshito(val conquistaId: String?) {
    PRIMEIRA_LEITURA("primeira_leitura"),
    LEITURA_POESIA(null), // Evento geral, pode ser usado para XP
    POESIA_FAVORITA("poesia_favorita"),
    // Adicione outros eventos conforme necessário
}
