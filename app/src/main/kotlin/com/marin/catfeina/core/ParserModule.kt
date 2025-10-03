/*
 * Arquivo: com.marin.catfeina.di.ParserModule.kt
 * @project Catfeina
 * @description
 * Módulo Hilt para fornecer instâncias relacionadas ao parsing de texto formatado.
 */
package com.marin.catfeina.di

// Importações NECESSÁRIAS para os processadores que você está usando
import com.marin.catfeina.ui.componentes.textoformatado.parser.ParserTextoFormatado
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorCabecalho
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorCitacao
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorEstiloSimples
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorImagem
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorItemLista
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorLinhaHorizontal
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorLink
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorTag // Interface
import com.marin.catfeina.ui.componentes.textoformatado.parser.ProcessadorTooltip

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber // Importação do Timber
import javax.inject.Singleton // Singleton já estava, mas confirmando

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    @Provides
    @Singleton
    fun provideProcessadoresTag(): List<ProcessadorTag> {
        Timber.d("Criando lista de ProcessadoresTag")
        return listOf(
            // Adicione aqui instâncias de TODOS os seus processadores concretos
            ProcessadorEstiloSimples(),
            ProcessadorLink(),
            ProcessadorTooltip(),
            ProcessadorCabecalho(),
            ProcessadorImagem(),
            ProcessadorCitacao(),
            ProcessadorLinhaHorizontal(),
            ProcessadorItemLista()
            // Certifique-se de que cada classe instanciada aqui existe, está importada
            // e tem um construtor público sem argumentos.
        )
    }

    @Provides
    @Singleton
    fun provideParserTextoFormatado(
        // Agora o Hilt injetará a List<ProcessadorTag> fornecida por provideProcessadoresTag()
        processadores: @JvmSuppressWildcards List<ProcessadorTag>
    ): ParserTextoFormatado {
        Timber.d("Fornecendo ParserTextoFormatado com ${processadores.size} processadores injetados.")
        return ParserTextoFormatado(processadores)
    }
}
