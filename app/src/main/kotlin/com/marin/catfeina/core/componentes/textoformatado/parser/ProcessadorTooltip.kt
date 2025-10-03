/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorTooltip.kt
 * @project Catfeina
 * @description Processador para tags de tooltip (ex: ::tooltip:texto do tooltip|texto anotado::).
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

// import androidx.wear.compose.foundation.size // REMOVIDA - Importação incorreta

import com.marin.catfeina.ui.componentes.textoformatado.AplicacaoAnotacaoTooltip
import timber.log.Timber

class ProcessadorTooltip : ProcessadorTag {
    override val palavrasChave: Set<String> = setOf("tooltip")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String, // Formato esperado: "texto do tooltip|texto que recebe o tooltip"
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        Timber.d("Processando TOOLTIP: Chave='${palavraChaveTag}', Conteúdo='${conteudoTag}'")

        // CORREÇÃO: Usando split com limite.
        // O resultado será uma lista. Se o delimitador não for encontrado, a lista terá um elemento.
        // Se for encontrado uma vez e o limite for 2, a lista terá dois elementos.
        val partes = conteudoTag.split("|", limit = 2)

        if (partes.size != 2) {
            Timber.w("Formato de conteúdo de tooltip inválido: '$conteudoTag'. Esperado 'tooltip|texto anotado'. Tratando como NaoConsumido.")
            return ResultadoProcessamentoTag.NaoConsumido
        }

        val textoTooltip = partes[0].trim()
        val textoAnotado = partes[1].trim() // Este é o texto que aparecerá no parágrafo

        if (textoTooltip.isBlank() || textoAnotado.isBlank()) {
            Timber.w("Texto do tooltip ou texto anotado está vazio. Tooltip='${textoTooltip}', Anotado='${textoAnotado}'. Tratando como NaoConsumido.")
            return ResultadoProcessamentoTag.NaoConsumido
        }

        return ResultadoProcessamentoTag.AplicacaoTagEmLinha(
            AplicacaoAnotacaoTooltip(
                intervalo = IntRange.EMPTY, // Será preenchido pelo parser principal
                textoOriginal = textoAnotado, // O texto que será inserido e anotado
                textoTooltip = textoTooltip,
                tagAnotacao = "tooltip_${textoTooltip.hashCode()}" // Tag única para a anotação
            )
        )
    }
}
