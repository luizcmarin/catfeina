// =============================================================================
// Arquivo: com.marin.catfeina.core.di.DataModule.kt
// Descrição: Módulo do Hilt para prover as dependências da camada de dados.
// =============================================================================
package com.marin.catfeina.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.marin.catfeina.core.data.AppDatabase
import com.marin.catfeina.core.data.daos.AtelierDao
import com.marin.catfeina.core.data.daos.HistoricoVisitaDao
import com.marin.catfeina.core.data.daos.InformativoDao
import com.marin.catfeina.core.data.daos.MarcadorDao
import com.marin.catfeina.core.data.daos.PersonagemDao
import com.marin.catfeina.core.data.daos.PoesiaDao
import com.marin.catfeina.core.data.daos.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

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
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun providePoesiaDao(database: AppDatabase): PoesiaDao = database.poesiaDao()

    @Provides
    fun providePersonagemDao(database: AppDatabase): PersonagemDao = database.personagemDao()

    @Provides
    fun provideInformativoDao(database: AppDatabase): InformativoDao = database.informativoDao()

    @Provides
    fun provideAtelierDao(database: AppDatabase): AtelierDao = database.atelierDao()

    @Provides
    fun provideHistoricoVisitaDao(database: AppDatabase): HistoricoVisitaDao = database.historicoVisitaDao()

    @Provides
    fun provideMarcadorDao(database: AppDatabase): MarcadorDao = database.marcadorDao()

    @Provides
    fun provideSearchDao(database: AppDatabase): SearchDao = database.searchDao()
}
