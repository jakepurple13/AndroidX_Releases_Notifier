package com.programmersbox.androidxreleasenotes

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("androidx")

val LAST_UPDATE = longPreferencesKey("last_update")
val Context.lastUpdate get() = dataStore.data.map { it[LAST_UPDATE] ?: 0 }

suspend fun <T> Context.updatePref(key: Preferences.Key<T>, value: T) = dataStore.edit { it[key] = value }

val currentColorScheme: ColorScheme
    @Composable
    get() {
        val darkTheme = isSystemInDarkTheme()
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
            darkTheme -> darkColorScheme(
                primary = Color(0xff90CAF9),
                secondary = Color(0xff90CAF9)
            )
            else -> lightColorScheme(
                primary = Color(0xff2196F3),
                secondary = Color(0xff90CAF9)
            )
        }
    }