/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorItemLista.kt
 * @project Catfeina
 * @description
 * Processador de tag responsável por identificar e processar tags de item de lista (ex: "::item:...::"),
 * convertendo-as em um ElementoConteudo.ItemLista.
 */
package com.marin.catfeina.ui.componentes.textoformatado.parser

import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo

/**
 * Processador de tag que lida com a palavra-chave "item" para criar elementos de item de lista.
 *
 * Exemplo de tag suportada: `::item:Este é o texto do item de lista::`
 */
class ProcessadorItemLista : ProcessadorTag {
    /**
     * Palavra-chave que este processador reconhece.
     */
    override val palavrasChave: Set<String> = setOf("item")

    /*** Processa a tag de item de lista.
     *
     * Quando uma tag "item" é encontrada, este método é chamado para convertê-la
     * em um [ElementoConteudo.ItemLista]. O [com.marin.catfeina.di.ParserTextoFormatado] principal
     * se encarrega de finalizar qualquer parágrafo pendente antes de adicionar
     * este elemento de bloco.
     *
     * Atualmente, o conteúdo da tag é tratado como texto simples para o item.
     * A formatação em linha (negrito, links, etc.) dentro do conteúdo do item
     * não é processada por este processador nesta versão (requer sub-parsing).
     *
     * @param palavraChaveTag A palavra-chave da tag que foi encontrada (deverá ser "item").
     * @param conteudoTag O conteúdo textual encontrado dentro da tag "item".
     * @param contexto O [ContextoParsing] atual, que pode ser usado para interagir com o estado do parser
     *                 (embora não seja utilizado nesta implementação simples de processador de bloco).
     * @return Um [ResultadoProcessamentoTag.ElementoBloco] contendo o [ElementoConteudo.ItemLista] criado.
     */
    override fun processar(
        palavraChaveTag: String,
        conteudoTag: String,
        contexto: ContextoParsing // Contexto é passado, mas não usado ativamente aqui
    ): ResultadoProcessamentoTag {
        // O ParserTextoFormatado.kt, ao receber um ElementoBloco,
        // já cuida de finalizar o parágrafo atual que estava sendo acumulado.
        // Portanto, NÃO precisamos chamar contexto.finalizarParagrafoAtualSeNecessario() aqui.

        // Cria o elemento de item de lista.
        // O `conteudoTag` é o texto do item.
        // As `aplicacoesEmLinha` são deixadas vazias por enquanto, o que significa que
        // tags de formatação DENTRO do conteúdo do item não serão processadas
        // por esta implementação simples. Para isso, seria necessário um sub-parsing
        // do `conteudoTag`.
        return ResultadoProcessamentoTag.ElementoBloco(
            ElementoConteudo.ItemLista(
                textoItem = conteudoTag,
                aplicacoesEmLinha = emptyList() // TODO: Implementar sub-parsing do conteudoTag se formatação interna for necessária
            )
        )
    }
}

