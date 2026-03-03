package ru.plumsoftware.avocado.ui.screen.main.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.food.allFood
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.screen.main.list.HarmoniousColors
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodColorCache

class FavoriteViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val context: Context // Добавляем контекст для поиска строк
) : ViewModel() {

    // --- ПОИСК ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    // --- ГЛАВНЫЙ ПОТОК ДАННЫХ ---
    @OptIn(FlowPreview::class)
    val favoriteFood: StateFlow<List<Food>> = combine(
        favoritesRepository.favoriteIds, // 1. Берем ID из базы
        _searchQuery.debounce(300L).onStart { emit("") } // 2. Берем поиск (с задержкой)
    ) { ids, query ->
        // Сначала получаем список объектов по ID
        val favorites = allFood.filter { it.id in ids }

        // Если поиск пустой — отдаем всё избранное
        if (query.isBlank()) {
            favorites
        } else {
            // Иначе фильтруем по названию
            favorites.filter { item ->
                val title = context.getString(item.titleRes)
                title.contains(query, ignoreCase = true)
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Проверка: есть ли вообще избранное (нужно для EmptyState, когда поиск не активен)
    // Мы слушаем напрямую репозиторий, чтобы знать, пуста ли база вообще
    val isDatabaseEmpty: StateFlow<Boolean> = favoritesRepository.favoriteIds
        .map { it.isEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)


    // --- ЦВЕТА И ЛАЙКИ ---
    private val colorCache = FoodColorCache()

    fun getBackgroundColorForFood(imageRes: Int, context: Context): Int {
        return colorCache.getBackgroundColor(imageRes, context)
    }

    fun getTextForColorForFood(imageRes: Int, context: Context): HarmoniousColors {
        return colorCache.getHarmoniousColors(imageRes, context)
    }

    fun onLikeClick(foodId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(foodId)
        }
    }

    companion object {
        class Factory(
            private val favoritesRepository: FavoritesRepository,
            private val context: Context // Передаем контекст в фабрику
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return FavoriteViewModel(favoritesRepository, context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}