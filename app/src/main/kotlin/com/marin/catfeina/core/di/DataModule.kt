// =============================================================================
// Arquivo: com.marin.catfeina.core.di.DataModule.kt
// Descrição: Módulo do Hilt para prover as dependências da camada de dados.
// =============================================================================
package com.marin.catfeina.core.di

import android.content.Context
import androidx.room.Room
import com.marin.catfeina.core.data.AppDatabase
import com.marin.catfeina.core.data.daos.AtelierDao
import com.marin.catfeina.core.data.daos.InformativoDao
import com.marin.catfeina.core.data.daos.PersonagemDao
import com.marin.catfeina.core.data.daos.PoesiaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    // CORREÇÃO: Voltando a injetar o Callback, a forma correta
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "catfeina.db"
        )
            .build()
    }

    @Provides
    fun providePoesiaDao(database: AppDatabase): PoesiaDao {
        return database.poesiaDao()
    }

    @Provides
    fun providePersonagemDao(database: AppDatabase): PersonagemDao {
        return database.personagemDao()
    }

    @Provides
    fun provideInformativoDao(database: AppDatabase): InformativoDao {
        return database.informativoDao()
    }

    @Provides
    fun provideAtelierDao(database: AppDatabase): AtelierDao {
        return database.atelierDao()
    }
}
