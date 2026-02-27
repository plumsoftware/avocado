package ru.plumsoftware.avocado.ui.screen.main.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.food.allFood
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.screen.main.list.HarmoniousColors
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodColorCache

class FavoriteViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    // --- ЛОГИКА: ID -> FOOD ---
    // Мы берем Flow из базы данных (Set<String>) и превращаем его в List<Food>
    val favoriteFood: StateFlow<List<Food>> = favoritesRepository.favoriteIds
        .map { ids ->
            // Фильтруем общий список: оставляем только те продукты, чей ID есть в базе
            allFood.filter { it.id in ids }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Кэш цветов (нужен для карточек)
    private val colorCache = FoodColorCache()

    fun getBackgroundColorForFood(imageRes: Int, context: Context): Int {
        return colorCache.getBackgroundColor(imageRes, context)
    }

    fun getTextForColorForFood(imageRes: Int, context: Context): HarmoniousColors {
        return colorCache.getHarmoniousColors(imageRes, context)
    }

    // Удаление из избранного (при нажатии на сердечко)
    fun onLikeClick(foodId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(foodId)
        }
    }

    companion object {
        class FavoriteViewModelFactory(
            private val favoritesRepository: FavoritesRepository,
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return FavoriteViewModel(favoritesRepository = favoritesRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}