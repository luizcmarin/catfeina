/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorCabecalho.kt
 * @project Catfeina
 * @description Processador para tags de cabeçalho (ex: ::t1:Texto Cabeçalho::).
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo
import timber.log.Timber

class ProcessadorCabecalho : ProcessadorTag {
    // Suporta tags como t1, t2, t3, etc.
    override val palavrasChave: Set<String> = setOf("t1", "t2", "t3", "t4", "t5", "t6")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String,
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        Timber.d("Processando CABEÇALHO: Chave='${palavraChaveTag}', Conteúdo='${conteudoTag}'")

        // Extrai o nível do cabeçalho da palavra-chave (ex: "t1" -> nível 1)
        val nivel = palavraChaveTag.removePrefix("t").toIntOrNull()
        if (nivel == null || nivel !in 1..6) {
            Timber.w("Nível de cabeçalho inválido para tag '$palavraChaveTag'. Tratando como NaoConsumido.")
            return ResultadoProcessamentoTag.NaoConsumido // Ou Erro, se preferir
        }

        return ResultadoProcessamentoTag.ElementoBloco(
            ElementoConteudo.Cabecalho(
                texto = conteudoTag,
                nivel = nivel
            )
        )
    }
}
