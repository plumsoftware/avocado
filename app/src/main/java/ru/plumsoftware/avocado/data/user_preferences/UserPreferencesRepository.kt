package ru.plumsoftware.avocado.data.user_preferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.snippets.proto.AppThemeProto
import ru.plumsoftware.avocado.snippets.proto.UserPreferences

class UserPreferencesRepository(
    private val dataStore: DataStore<UserPreferences>
) {

    val isFirstLaunch: Flow<Boolean> =
        dataStore.data.map { it.firstLaunch }

    val appTheme: Flow<AppTheme> = dataStore.data.map { prefs ->
        // Превращаем Proto-enum (из файла .proto) в наш Kotlin-enum
        when (prefs.appTheme) {
            AppThemeProto.LIGHT -> AppTheme.Light
            AppThemeProto.DARK -> AppTheme.Dark
            AppThemeProto.SYSTEM,
            AppThemeProto.UNRECOGNIZED,
            null -> AppTheme.System // По умолчанию
        }
    }

    suspend fun setAppTheme(theme: AppTheme) {
        val protoTheme = when (theme) {
            AppTheme.Light -> AppThemeProto.LIGHT
            AppTheme.Dark -> AppThemeProto.DARK
            AppTheme.System -> AppThemeProto.SYSTEM
        }

        dataStore.updateData { prefs ->
            prefs.toBuilder()
                .setAppTheme(protoTheme)
                .build()
        }
    }

    suspend fun markFirstLaunchDone() {
        dataStore.updateData { prefs ->
            prefs.toBuilder()
                .setFirstLaunch(false)
                .build()
        }
    }
}
