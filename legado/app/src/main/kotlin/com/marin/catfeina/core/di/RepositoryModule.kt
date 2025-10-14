// =============================================================================
// Arquivo: com.marin.catfeina.core.di.RepositoryModule.kt
// Descrição: Módulo do Hilt para prover as implementações dos repositórios.
// =============================================================================
package com.marin.catfeina.core.di

import com.marin.catfeina.core.data.repository.AtelierRepository
import com.marin.catfeina.core.data.repository.AtelierRepositoryImpl
import com.marin.catfeina.core.data.repository.ConquistasRepository
import com.marin.catfeina.core.data.repository.ConquistasRepositoryImpl
import com.marin.catfeina.core.data.repository.HistoricoRepository
import com.marin.catfeina.core.data.repository.HistoricoRepositoryImpl
import com.marin.catfeina.core.data.repository.InformativoRepository
import com.marin.catfeina.core.data.repository.InformativoRepositoryImpl
import com.marin.catfeina.core.data.repository.MarcadorRepository
import com.marin.catfeina.core.data.repository.MarcadorRepositoryImpl
import com.marin.catfeina.core.data.repository.PersonagemRepository
import com.marin.catfeina.core.data.repository.PersonagemRepositoryImpl
import com.marin.catfeina.core.data.repository.PoesiaRepository
import com.marin.catfeina.core.data.repository.PoesiaRepositoryImpl
import com.marin.catfeina.features.search.SearchRepository
import com.marin.catfeina.features.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPoesiaRepository(poesiaRepositoryImpl: PoesiaRepositoryImpl): PoesiaRepository

    @Binds
    @Singleton
    abstract fun bindPersonagemRepository(personagemRepositoryImpl: PersonagemRepositoryImpl): PersonagemRepository

    @Binds
    @Singleton
    abstract fun bindInformativoRepository(informativoRepositoryImpl: InformativoRepositoryImpl): InformativoRepository

    @Binds
    @Singleton
    abstract fun bindAtelierRepository(atelierRepositoryImpl: AtelierRepositoryImpl): AtelierRepository

    @Binds
    @Singleton
    abstract fun bindHistoricoRepository(historicoRepositoryImpl: HistoricoRepositoryImpl): HistoricoRepository

    @Binds
    @Singleton
    abstract fun bindMarcadorRepository(marcadorRepositoryImpl: MarcadorRepositoryImpl): MarcadorRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindConquistasRepository(conquistasRepositoryImpl: ConquistasRepositoryImpl): ConquistasRepository
}
