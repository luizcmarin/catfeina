/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorEstiloSimples.kt
 * @project Catfeina
 * @description Processador para tags de estilo simples em linha (negrito, itálico, destaque, sublinhado)
 *              usando a sintaxe {chave|conteúdo}.
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.marin.catfeina.ui.componentes.textoformatado.AplicacaoSpanStyle // VERIFIQUE ESTE CAMINHO
import timber.log.Timber

class ProcessadorEstiloSimples : ProcessadorTag {
    override val palavrasChave: Set<String> = setOf("n", "d", "i", "s", "ni")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String,
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        Timber.d("[ProcessadorEstiloSimples] Recebido: Chave='${palavraChaveTag}', ConteúdoInterno='${conteudoTag.replace("\n", "\\n")}'")

        val aplicacaoStyle = when (palavraChaveTag.lowercase()) {
            "n" -> {
                AplicacaoSpanStyle(
                    textoOriginal = conteudoTag,
                    fontWeight = FontWeight.Bold // USA 'fontWeight'
                )
            }
            "d" -> {
                AplicacaoSpanStyle(
                    textoOriginal = conteudoTag,
                    isDestaque = true // USA 'isDestaque'
                )
            }
            "i" -> {
                AplicacaoSpanStyle(
                    textoOriginal = conteudoTag,
                    fontStyle = FontStyle.Italic // USA 'fontStyle'
                )
            }
            "s" -> {
                AplicacaoSpanStyle(
                    textoOriginal = conteudoTag,
                    textDecoration = TextDecoration.Underline // USA 'textDecoration'
                )
            }
            "ni" -> {
                AplicacaoSpanStyle(
                    textoOriginal = conteudoTag,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }
            else -> {
                Timber.w("[ProcessadorEstiloSimples] Palavra-chave não reconhecida: '$palavraChaveTag'.")
                return ResultadoProcessamentoTag.NaoConsumido
            }
        }
        // Não passamos 'intervalo' aqui porque AplicacaoSpanStyle tem valor padrão.
        // Não usamos 'provedorSpanStyle' nesta abordagem.
        return ResultadoProcessamentoTag.AplicacaoTagEmLinha(aplicacaoStyle)
    }
}
