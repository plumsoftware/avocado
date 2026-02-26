package ru.plumsoftware.avocado.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.data.onboarding.UserRestriction
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Состояния запуска
    sealed interface StartDestination {
        data object Loading : StartDestination
        data object Onboarding : StartDestination
        data object Main : StartDestination
    }

    // Слушаем репозиторий: если онбординг пройден -> Main, иначе -> Onboarding
    val startDestination: StateFlow<StartDestination> = userPreferencesRepository.isOnboardingCompleted
        .map { isCompleted ->
            if (isCompleted) StartDestination.Main else StartDestination.Onboarding
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StartDestination.Loading
        )

    // Вызываем этот метод, когда юзер жмет кнопку "Начать"
    fun finishOnboarding(goals: List<UserGoal>, restrictions: List<UserRestriction>) {
        viewModelScope.launch {
            userPreferencesRepository.completeOnboarding(goals, restrictions)
        }
    }

    companion object {
        class MainViewModelFactory(private val userPreferencesRepository: UserPreferencesRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(userPreferencesRepository = userPreferencesRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}