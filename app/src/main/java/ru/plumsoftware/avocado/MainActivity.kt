package ru.plumsoftware.avocado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.data.user_preferences.userPreferencesDataStore
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.MainScreen
import ru.plumsoftware.avocado.ui.theme.AvocadoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferencesRepository =
            UserPreferencesRepository(dataStore = this.userPreferencesDataStore)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        setContent {

            val navController = rememberNavController()

            AvocadoTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppDestination.MainScreen
                ) {
                    composable<AppDestination.MainScreen> {
                        MainScreen(
                            userPreferencesRepository = userPreferencesRepository
                        )
                    }
                }
            }
        }
    }
}
