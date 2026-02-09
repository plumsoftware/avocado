package ru.plumsoftware.avocado.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.screen.main.elements.BottomNavBar

@Composable
fun MainScreen(userPreferencesRepository: UserPreferencesRepository) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModel.Companion.MainViewModelFactory(userPreferencesRepository = userPreferencesRepository)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
            ) {
                BottomNavBar()
            }
        }
    }
}