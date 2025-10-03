/*
 * Arquivo: com.marin.catfeina.data.UserPreferencesRepository.kt
 * Salva preferencias de usuário no DataStore.
 */
package com.marin.catfeina.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Cria uma instância do DataStore como um singleton, associada ao Contexto da aplicação. [5]
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    // Define as chaves para as preferências que você quer salvar. [3]
    private object PreferencesKeys {
        // Exemplo: uma preferência para tema escuro
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    // Expõe um Flow para ler o valor da preferência. O Flow emitirá um novo valor sempre que a preferência mudar. [5]
    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            // Lê o valor booleano. Se não existir, retorna 'false' como padrão.
            preferences[PreferencesKeys.IS_DARK_MODE] ?: false
        }

    // Cria uma função suspend para atualizar a preferência de forma segura. [1]
    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode
        }
    }
}
