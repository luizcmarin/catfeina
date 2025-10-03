/* * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorImagem.kt
 * @project Catfeina
 * @description
 * Processador para a tag de imagem (::imagem:nomeArquivo|textoAlternativo::).
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo

class ProcessadorImagem : ProcessadorTag {
    override val palavrasChave: Set<String> = setOf("imagem")

    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String,
        contexto: ContextoParsing
    ): ResultadoProcessamentoTag {
        val partes = conteudoTag.split('|', limit = 2)
        if (partes.size != 2) {
            return ResultadoProcessamentoTag.Erro(
                "Tag 'imagem' mal formatada. Esperado 'nomeArquivo|textoAlternativo'. Conteúdo: $conteudoTag"
            )
        }
        val nomeArquivo = partes[0].trim()
        val textoAlternativo = partes[1].trim()

        if (nomeArquivo.isBlank()) {
            return ResultadoProcessamentoTag.Erro("Nome do arquivo da imagem não pode ser vazio.")
        }

        return ResultadoProcessamentoTag.ElementoBloco(
            ElementoConteudo.Imagem(
                nomeArquivo = nomeArquivo,
                textoAlternativo = textoAlternativo
            )
        )
    }
}
