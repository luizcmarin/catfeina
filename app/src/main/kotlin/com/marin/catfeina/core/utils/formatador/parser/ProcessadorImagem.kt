// ===================================================================================
// Arquivo: com.marin.catfeina.core.utils.formatador.parser.ProcessadorImagem.kt
//
// Descrição: Processador de tag especializado em converter tags de imagem
//            (ex: {imagem|gato.png|Um gato olhando a paisagem}) em um
//            `ElementoConteudo.Imagem`.
//
// Propósito:
// Esta classe isola a lógica para lidar com a inserção de imagens no texto.
// Registrada no `ParserModule`, ela é invocada pelo `ParserTextoFormatado`
// ao encontrar a palavra-chave "imagem". Sua responsabilidade é extrair o
// nome do arquivo e o texto alternativo, retornando um elemento de bloco
// que a UI usará (com a ajuda da biblioteca Coil) para carregar e exibir
// a imagem a partir dos assets do aplicativo.
// ===================================================================================
package com.marin.catfeina.core.utils.formatador.parser

import com.marin.catfeina.core.utils.formatador.ElementoConteudo

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
