// =============================================================================
// Arquivo: com.marin.catfeina.core.di.ServiceModule.kt
// Descrição: Módulo do Hilt para prover as implementações dos serviços.
// =============================================================================
package com.marin.catfeina.core.di

import com.marin.catfeina.core.data.repository.ConquistasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideCatshitoService(conquistasRepository: ConquistasRepository): CatshitoService {
        return CatshitoService(conquistasRepository)
    }
}
