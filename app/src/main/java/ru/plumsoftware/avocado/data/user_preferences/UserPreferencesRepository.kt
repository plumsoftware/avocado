package ru.plumsoftware.avocado.data.user_preferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.plumsoftware.avocado.snippets.proto.UserPreferences

class UserPreferencesRepository(
    private val dataStore: DataStore<UserPreferences>
) {

    val isFirstLaunch: Flow<Boolean> =
        dataStore.data.map { it.firstLaunch }

    suspend fun markFirstLaunchDone() {
        dataStore.updateData { prefs ->
            prefs.toBuilder()
                .setFirstLaunch(false)
                .build()
        }
    }
}
