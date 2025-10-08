package com.marin.catfeina.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marin.catfeina.core.theme.ThemeModelKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(@param:ApplicationContext private val context: Context) {

    private object Keys {
        val SELECTED_THEME_KEY = stringPreferencesKey("selected_theme_key")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    /**
     * Expõe a chave do tema selecionado como um Flow.
     * Se nada estiver salvo, retorna a chave do tema padrão (WHITE).
     */
    val selectedThemeKey: Flow<ThemeModelKey> = context.dataStore.data.map { preferences ->
        val keyName = preferences[Keys.SELECTED_THEME_KEY] ?: ThemeModelKey.VERAO.name // Corrigido de WHITE para VERAO
        try {
            ThemeModelKey.valueOf(keyName)
        } catch (e: IllegalArgumentException) {
            ThemeModelKey.VERAO // Corrigido de WHITE para VERAO
        }
    }
    /**
     * Expõe a preferência de modo escuro como um Flow.
     */
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[Keys.IS_DARK_MODE] ?: false
    }

    /**
     * Salva a chave do tema selecionado no DataStore.
     */
    suspend fun setSelectedThemeKey(themeKey: ThemeModelKey) {
        context.dataStore.edit { preferences ->
            preferences[Keys.SELECTED_THEME_KEY] = themeKey.name
        }
    }

    /**
     * Salva a preferência de modo escuro no DataStore.
     */
    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Keys.IS_DARK_MODE] = isDarkMode
        }
    }
}
