/*
 * Arquivo: com.marin.catfeina.ui.componentes.textoformatado.ElementosConteudo.kt
 * @project Catfeina
 * @description
 * Define as estruturas de dados para representar o conteúdo de texto formatado
 * após o processamento pelo parser. Inclui elementos de bloco como parágrafos,
 * cabeçalhos, imagens, e também representações para estilos e anotações em linha
 * que serão usados para construir um AnnotatedString.
 */
package com.marin.catfeina.ui.componentes.textoformatado

// androidx.compose.ui.text.SpanStyle NÃO É USADO DIRETAMENTE AQUI, PODE SER REMOVIDO SE NÃO FOR USADO EM OUTRO LUGAR DESTE ARQUIVO
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import java.util.UUID

/**
 * Representa um estilo ou anotação a ser aplicado a um trecho de texto dentro de um parágrafo.
 */
sealed interface AplicacaoEmLinha { // <<---- idUnico REMOVIDO DESTA INTERFACE
    /** O intervalo no texto original do parágrafo onde esta formatação se aplica. */
    var intervalo: IntRange

    /** O texto original que esta aplicação de formatação engloba (sem as tags). */
    val textoOriginal: String
}

/**
 * Representa a aplicação de um SpanStyle (ex: negrito, itálico, cor de fundo para destaque).
 */
data class AplicacaoSpanStyle( // <<---- NÃO PRECISA MAIS DE idUnico
    override val textoOriginal: String,
    override var intervalo: IntRange = IntRange.EMPTY,
    val fontWeight: FontWeight? = null,
    val fontStyle: FontStyle? = null,
    val textDecoration: TextDecoration? = null,
    val isDestaque: Boolean = false
) : AplicacaoEmLinha

/**
 * Representa um link clicável a ser anotado no texto.
 */
data class AplicacaoAnotacaoLink( // <<---- NÃO PRECISA MAIS DE idUnico
    override var intervalo: IntRange, // Se não tiver valor padrão, o parser precisa sempre fornecer
    override val textoOriginal: String,
    val url: String,
    val tagAnotacao: String = "URL_${url.hashCode()}" // Tornando a tag um pouco mais única
) : AplicacaoEmLinha

/**
 * Representa um tooltip (dica de contexto) a ser associado a um trecho de texto.
 */
data class AplicacaoAnotacaoTooltip( // <<---- NÃO PRECISA MAIS DE idUnico
    override var intervalo: IntRange, // Se não tiver valor padrão, o parser precisa sempre fornecer
    override val textoOriginal: String,
    val textoTooltip: String,
    val tagAnotacao: String = "TOOLTIP_${textoTooltip.hashCode()}" // Tornando a tag um pouco mais única
) : AplicacaoEmLinha


// --- Elementos de Conteúdo de Bloco ---

/**
 * Representa um elemento de bloco no conteúdo estruturado.
 * O parser irá converter o texto cru em uma lista de [ElementoConteudo]s.
 */
sealed interface ElementoConteudo {
    val idUnico: String // <<---- DEFINIDO AQUI, NA INTERFACE PAI DOS ITENS DA LAZYCOLUMN

    data class Paragrafo(
        val textoCru: String,
        val aplicacoesEmLinha: List<AplicacaoEmLinha>,
        override val idUnico: String = UUID.randomUUID().toString()
    ) : ElementoConteudo

    data class Cabecalho(
        val nivel: Int,
        val texto: String,
        override val idUnico: String = UUID.randomUUID().toString()
    ) : ElementoConteudo

    data class Imagem(
        val nomeArquivo: String,
        val textoAlternativo: String? = null,
        override val idUnico: String = UUID.randomUUID().toString()
    ) : ElementoConteudo

    data class Citacao(
        val texto: String,
        override val idUnico: String = UUID.randomUUID().toString()
    ) : ElementoConteudo

    object LinhaHorizontal : ElementoConteudo {
        override val idUnico: String = "ELEMENTO_LINHA_HORIZONTAL_SINGLETON" // ID Fixo e único para o singleton
    }

    data class ItemLista(
        val textoItem: String,
        val aplicacoesEmLinha: List<AplicacaoEmLinha> = emptyList(),
        override val idUnico: String = UUID.randomUUID().toString()
    ) : ElementoConteudo
}
