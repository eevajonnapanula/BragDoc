package com.eevajonna.bragdocument.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface SettingsDataStore {
    suspend fun saveLanguageSelectionEnabled(languageSelectionEnabled: Boolean)
    val languageSelectionEnabled: Flow<Boolean>
}

class SettingsDataStoreImpl(
    private val settingsDataStore: DataStore<Preferences>,
) : SettingsDataStore {
    private val langSelectionEnabled = booleanPreferencesKey("lang_selection_enabled")

    override val languageSelectionEnabled: Flow<Boolean> = settingsDataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[langSelectionEnabled] ?: false
        }

    override suspend fun saveLanguageSelectionEnabled(
        languageSelectionEnabled: Boolean,
    ) {
        settingsDataStore.edit { preferences ->
            preferences[langSelectionEnabled] = languageSelectionEnabled
        }
    }
}

const val SETTINGS_DATA_STORE = "settings_data_store"
