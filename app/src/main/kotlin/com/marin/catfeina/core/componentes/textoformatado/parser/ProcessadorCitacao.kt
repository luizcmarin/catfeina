/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorCitacao.kt
 * @project Catfeina
 * @description
 * Processador para a tag de citação (::cit:texto da citação::).
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo

class ProcessadorCitacao : ProcessadorTag {
    override val palavrasChave: Set<String> = setOf("cit")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String,
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        if (conteudoTag.isBlank()) {
            // Pode ser permitido uma citação vazia ou retornar um erro/aviso
            // Por ora, vamos permitir, resultando em um bloco de citação vazio.
        }
        return ResultadoProcessamentoTag.ElementoBloco(
            ElementoConteudo.Citacao(texto = conteudoTag)
        )
    }
}