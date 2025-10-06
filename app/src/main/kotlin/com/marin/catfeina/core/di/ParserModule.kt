// ===================================================================================
// Arquivo: com.marin.catfeina.core.di.ParserModule.kt
//
// Descrição: Módulo de injeção de dependência (Hilt) responsável por configurar e
//            fornecer todas as classes relacionadas à análise (parsing) de texto
//            formatado no aplicativo.
//
// Propósito:
// Este módulo centraliza a criação do `ParserTextoFormatado` e de sua principal
// dependência: uma lista de `ProcessadorTag`. Ao usar o Hilt, garantimos que
// uma única instância (Singleton) do parser seja compartilhada em todo o app,
// promovendo eficiência e um design desacoplado.
//
// A arquitetura aqui permite fácil extensibilidade: para suportar uma nova tag de
// formatação, basta criar uma nova classe `ProcessadorTag` e adicioná-la à
// lista em `provideProcessadoresTag()`, sem a necessidade de alterar o resto do
// código da aplicação.
// ===================================================================================

package com.marin.catfeina.core.di

import com.marin.catfeina.ui.composables.formatador.parser.ParserTextoFormatado
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorCabecalho
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorCitacao
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorEstiloSimples
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorImagem
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorItemLista
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorLinhaHorizontal
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorLink
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorTag
import com.marin.catfeina.ui.composables.formatador.parser.ProcessadorTooltip
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    @Provides
    @Singleton
    fun provideProcessadoresTag(
        // CORREÇÃO: Hilt agora injeta o ProcessadorItemLista aqui, nos parâmetros da função.
        processadorItemLista: ProcessadorItemLista
    ): List<ProcessadorTag> {
        Timber.Forest.d("Criando lista de ProcessadoresTag")
        return listOf(
            // Processadores que não têm dependências podem ser instanciados diretamente.
            ProcessadorEstiloSimples(),
            ProcessadorLink(),
            ProcessadorTooltip(),
            ProcessadorCabecalho(),
            ProcessadorImagem(),
            ProcessadorCitacao(),
            ProcessadorLinhaHorizontal(),
            // CORREÇÃO: Usamos a instância de 'processadorItemLista' injetada pelo Hilt.
            processadorItemLista
        )
    }

    @Provides
    @Singleton
    fun provideParserTextoFormatado(
        // @JvmSuppressWildcards é necessário porque o Hilt injeta List<out ProcessadorTag>
        processadores: @JvmSuppressWildcards List<ProcessadorTag>
    ): ParserTextoFormatado {
        Timber.Forest.d("Fornecendo ParserTextoFormatado com ${processadores.size} processadores injetados.")
        return ParserTextoFormatado(processadores)
    }
}
