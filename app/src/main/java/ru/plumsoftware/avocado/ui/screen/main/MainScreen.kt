package ru.plumsoftware.avocado.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.screen.main.elements.BottomNavBar
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsScreen
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsViewModel
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun MainScreen(
    userPreferencesRepository: UserPreferencesRepository,
    settingsViewModel: SettingsViewModel
) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModel.Companion.MainViewModelFactory(userPreferencesRepository = userPreferencesRepository)
    )

    var currentRoute by remember { mutableStateOf<MainScreenStates>(MainScreenStates.Empty) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (currentRoute) {
                is MainScreenStates.List -> {}
                MainScreenStates.Fav -> {}
                MainScreenStates.Rec -> {}
                MainScreenStates.Settings -> {
                    SettingsScreen(
                        viewModel = settingsViewModel
                    )
                }

                MainScreenStates.Empty -> {}
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(0.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 48.dp)
                    .align(Alignment.BottomCenter)
            ) {
                BottomNavBar(
                    onItemSelected = { newItem ->
                        currentRoute = newItem
                    }
                )
            }
        }
    }
}