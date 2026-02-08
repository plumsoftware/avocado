package ru.plumsoftware.avocado.data.user_preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import ru.plumsoftware.avocado.snippets.proto.UserPreferences

val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_preferences.pb",
    serializer = UserPreferencesSerializer
)
