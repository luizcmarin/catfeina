package com.marin.catfeina.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.marin.catfeina.core.theme.ThemeModelKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val ONBOARDING_COMPLETO = booleanPreferencesKey("onboarding_completo")
        val SELECTED_THEME_KEY = stringPreferencesKey("selected_theme_key")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val TTS_VOICE_ID = stringPreferencesKey("tts_voice_id")
    }

    val onboardingCompleto: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[Keys.ONBOARDING_COMPLETO] ?: false
    }

    val selectedThemeKey: Flow<ThemeModelKey> = dataStore.data.map { preferences ->
        val keyName = preferences[Keys.SELECTED_THEME_KEY] ?: ThemeModelKey.VERAO.name
        try {
            ThemeModelKey.valueOf(keyName)
        } catch (e: IllegalArgumentException) {
            ThemeModelKey.VERAO
        }
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[Keys.IS_DARK_MODE] ?: false
    }

    val selectedTtsVoiceId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Keys.TTS_VOICE_ID]
    }

    suspend fun setOnboardingCompleto(isCompleto: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.ONBOARDING_COMPLETO] = isCompleto
        }
    }

    suspend fun setSelectedThemeKey(themeKey: ThemeModelKey) {
        dataStore.edit { preferences ->
            preferences[Keys.SELECTED_THEME_KEY] = themeKey.name
        }
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.IS_DARK_MODE] = isDarkMode
        }
    }

    suspend fun setSelectedTtsVoiceId(voiceId: String) {
        dataStore.edit { preferences ->
            preferences[Keys.TTS_VOICE_ID] = voiceId
        }
    }
}
