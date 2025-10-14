// =============================================================================
// Arquivo: com.marin.catfeina.core.di.NetworkModule.kt
// Descrição: Módulo do Hilt para prover as dependências da camada de rede.
// =============================================================================
package com.marin.catfeina.core.di

import com.marin.catfeina.core.data.remote.CatfeinaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // TODO: A BASE_URL deve ser movida para um local mais apropriado, como um BuildConfig.
    private const val BASE_URL = "https://drive.google.com/"

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCatfeinaApiService(retrofit: Retrofit): CatfeinaApiService {
        return retrofit.create(CatfeinaApiService::class.java)
    }
}
