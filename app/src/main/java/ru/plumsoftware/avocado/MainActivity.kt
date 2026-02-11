package ru.plumsoftware.avocado

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.data.user_preferences.util.userPreferencesDataStore
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.MainScreen
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsViewModel
import ru.plumsoftware.avocado.ui.theme.AvocadoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferencesRepository =
            UserPreferencesRepository(dataStore = this.userPreferencesDataStore)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = TRANSPARENT,
                darkScrim = TRANSPARENT
            )
        )

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemGestures()
            )
            view.updatePadding(
                0,
                0,
                0,
                0
            )
            WindowInsetsCompat.CONSUMED
        }


        setContent {

            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.Factory(repo = userPreferencesRepository)
            )

            val themeState by settingsViewModel.currentTheme.collectAsState()

            val isDark = when (themeState) {
                AppTheme.Light -> false
                AppTheme.Dark -> true
                AppTheme.System -> isSystemInDarkTheme()
            }

            AvocadoTheme(
                darkTheme = isDark
            ) {
                NavHost(
                    navController = navController,
                    startDestination = AppDestination.MainScreen
                ) {
                    composable<AppDestination.MainScreen> {
                        MainScreen(
                            userPreferencesRepository = userPreferencesRepository,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}
