// =============================================================================
// Arquivo: com.marin.catfeina.core.di.RepositoryModule.kt
// Descrição: Módulo do Hilt para prover as implementações dos repositórios.
// =============================================================================
package com.marin.catfeina.core.di

import com.marin.catfeina.core.data.repository.AtelierRepository
import com.marin.catfeina.core.data.repository.AtelierRepositoryImpl
import com.marin.catfeina.core.data.repository.PersonagemRepository
import com.marin.catfeina.core.data.repository.PersonagemRepositoryImpl
import com.marin.catfeina.core.data.repository.PoesiaRepository
import com.marin.catfeina.core.data.repository.PoesiaRepositoryImpl
import com.marin.catfeina.core.data.repository.InformativoRepository
import com.marin.catfeina.core.data.repository.InformativoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Informa ao Hilt que, sempre que um PoesiaRepository for solicitado,
     * ele deve prover uma instância de PoesiaRepositoryImpl.
     * A anotação @Singleton garante que haverá apenas uma instância do repositório
     * durante todo o ciclo de vida da aplicação.
     */
    @Binds
    @Singleton
    abstract fun bindPoesiaRepository(
        poesiaRepositoryImpl: PoesiaRepositoryImpl
    ): PoesiaRepository

    /**
     * Informa ao Hilt que, sempre que um PersonagemRepository for solicitado,
     * ele deve prover uma instância de PersonagemRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindPersonagemRepository(
        personagemRepositoryImpl: PersonagemRepositoryImpl
    ): PersonagemRepository

    /**
     * Informa ao Hilt que, sempre que um InformativoRepository for solicitado,
     * ele deve prover uma instância de InformativoRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindInformativoRepository(
        informativoRepositoryImpl: InformativoRepositoryImpl
    ): InformativoRepository

    /**
     * Informa ao Hilt que, sempre que um AtelierRepository for solicitado,
     * ele deve prover uma instância de AtelierRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindAtelierRepository(
        atelierRepositoryImpl: AtelierRepositoryImpl
    ): AtelierRepository
}

