package ru.plumsoftware.avocado.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {



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