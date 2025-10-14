// =============================================================================
// Arquivo: com.marin.catfeina.core.data.repository.ConquistasRepositoryImpl.kt
// Descrição: Implementação do repositório de conquistas usando DataStore.
// =============================================================================
package com.marin.catfeina.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object PreferenciasChaves {
    val CONQUISTAS_DESBLOQUEADAS = stringSetPreferencesKey("conquistas_desbloqueadas")
}

class ConquistasRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ConquistasRepository {

    override fun getConquistasDesbloqueadas(): Flow<Set<String>> {
        return dataStore.data.map { preferences ->
            preferences[PreferenciasChaves.CONQUISTAS_DESBLOQUEADAS] ?: emptySet()
        }
    }

    override suspend fun adicionarConquista(conquistaId: String) {
        dataStore.edit { preferences ->
            val conquistasAtuais = preferences[PreferenciasChaves.CONQUISTAS_DESBLOQUEADAS] ?: emptySet()
            preferences[PreferenciasChaves.CONQUISTAS_DESBLOQUEADAS] = conquistasAtuais + conquistaId
        }
    }
}
