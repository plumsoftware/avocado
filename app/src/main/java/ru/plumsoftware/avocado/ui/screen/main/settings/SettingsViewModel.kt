package ru.plumsoftware.avocado.ui.screen.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme

class SettingsViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    val currentTheme: StateFlow<AppTheme> = repository.appTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppTheme.System
        )

    fun updateTheme(newTheme: AppTheme) {
        viewModelScope.launch {
            repository.setAppTheme(newTheme)
        }
    }

    // Фабрика для создания ViewModel с параметрами
    class Factory(private val repo: UserPreferencesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(repo) as T
        }
    }
}