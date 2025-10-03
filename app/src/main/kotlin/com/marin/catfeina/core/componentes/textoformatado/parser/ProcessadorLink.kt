/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorLink.kt
 * @project Catfeina
 * @description Processador para tags de link (ex: ::link:url|texto do link::).
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

// import androidx.wear.compose.foundation.size // REMOVIDA
import com.marin.catfeina.ui.componentes.textoformatado.AplicacaoAnotacaoLink
import timber.log.Timber

class ProcessadorLink : ProcessadorTag {
    override val palavrasChave: Set<String> = setOf("link", "url")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String, // Formato esperado: "url|texto do link"
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        Timber.d("Processando LINK: Chave='${palavraChaveTag}', Conteúdo='${conteudoTag}'")

        // CORREÇÃO: Usando split com limite.
        val partes = conteudoTag.split("|", limit = 2)

        if (partes.size != 2) {
            Timber.w("Formato de conteúdo de link inválido: '$conteudoTag'. Esperado 'url|texto'. Tratando como NaoConsumido.")
            return ResultadoProcessamentoTag.NaoConsumido
        }

        val url = partes[0].trim()
        val textoLink = partes[1].trim()

        if (url.isBlank() || textoLink.isBlank()) {
            Timber.w("URL ou texto do link está vazio. URL='${url}', Texto='${textoLink}'. Tratando como NaoConsumido.")
            return ResultadoProcessamentoTag.NaoConsumido
        }

        return ResultadoProcessamentoTag.AplicacaoTagEmLinha(
            AplicacaoAnotacaoLink(
                intervalo = IntRange.EMPTY,
                textoOriginal = textoLink,
                url = url
            )
        )
    }
}
