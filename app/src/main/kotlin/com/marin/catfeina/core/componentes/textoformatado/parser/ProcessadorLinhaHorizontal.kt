/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorLinhaHorizontal.kt
 * @project Catfeina
 * @description
 * Processador para a tag de linha horizontal (::linha::).
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo

class ProcessadorLinhaHorizontal : ProcessadorTag {
    override val palavrasChave: Set<String> = setOf("linha")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String, // Conteúdo é ignorado para ::linha::
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        // Validação adicional: verificar se conteudoTag está vazio, como esperado.
        if (conteudoTag.isNotEmpty()) {
            // Logar um aviso, mas ainda assim processar como linha.
            println("Aviso: Tag 'linha' continha texto inesperado: '$conteudoTag'. Ignorando conteúdo.")
        }
        return ResultadoProcessamentoTag.ElementoBloco(
            ElementoConteudo.LinhaHorizontal
        )
    }
}
