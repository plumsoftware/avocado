package ru.plumsoftware.avocado.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.screen.main.elements.BottomNavBar
import ru.plumsoftware.avocado.ui.screen.main.list.MainListScreen
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsScreen
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsViewModel

@Composable
fun MainScreen(
    userPreferencesRepository: UserPreferencesRepository,
    favoritesRepository: FavoritesRepository,
    settingsViewModel: SettingsViewModel,
    navController: NavHostController
) {

    var currentRoute by remember { mutableStateOf<MainScreenStates>(MainScreenStates.Empty) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            when (currentRoute) {
                is MainScreenStates.List -> {
                    MainListScreen(
                        navController = navController,
                        favoritesRepository = favoritesRepository,
                        userPreferencesRepository = userPreferencesRepository
                    )
                }

                MainScreenStates.Fav -> {}
                MainScreenStates.Rec -> {}
                MainScreenStates.Settings -> {
                    SettingsScreen(
                        viewModel = settingsViewModel
                    )
                }

                MainScreenStates.Empty -> {}
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.background
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