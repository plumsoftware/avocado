package ru.plumsoftware.avocado.ui.screen.main.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.food.allFood
import ru.plumsoftware.avocado.data.database.AvocadoDatabase
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.ui.log
import ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.Filter
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodColorCache

class ListViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val filters_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.filters.toMutableList())
    val filters = filters_.asStateFlow()

    private val selectedFilter_ = MutableStateFlow(Filter.empty())
    val selectedFilter = selectedFilter_.asStateFlow()

    private val recomendedOnBreakfast_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.recomendedOnBreakfast.toMutableList())
    private val withFiber_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.withFiber.toMutableList())
    val recomendedOnBreakfast = recomendedOnBreakfast_.asStateFlow()
    val withFiber = withFiber_.asStateFlow()

    val heavyProtein_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.top7HighProteinFoods.toMutableList())
    val heavyProtein = heavyProtein_.asStateFlow()

    val healthyFatsItems_ = MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.topOmega3.toMutableList())
    val healthyFatsItems = healthyFatsItems_.asStateFlow()

    val allFood_ = MutableStateFlow(ru.plumsoftware.avocado.data.base.model.food.allFood)
    val allFood = allFood_.asStateFlow()

    val favoriteIds: StateFlow<Set<String>> = favoritesRepository.favoriteIds
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val colorCache = FoodColorCache()

    fun onLikeClick(foodId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(foodId)
        }
    }

    // Проверка для UI
    fun isFavorite(foodId: String): Boolean {
        return favoriteIds.value.contains(foodId)
    }

    fun getBackgroundColorForFood(imageRes: Int, context: Context): Int {
        return colorCache.getBackgroundColor(imageRes, context)
    }

    fun getTextForColorForFood(imageRes: Int, context: Context): HarmoniousColors {
        return colorCache.getHarmoniousColors(imageRes, context)
    }

    fun getLighterColorForFood(imageRes: Int, context: Context): Int {
        return getLightenedColor(getBackgroundColorForFood(imageRes = imageRes, context = context))
    }

    fun updateSelectedFilter(newFilter: Filter) {
        // Обновляем список фильтров
        filters_.update { oldList ->
            oldList.map { filter ->
                if (filter.id == newFilter.id) {
                    // Инвертируем состояние выбранного фильтра
                    filter.copy(isSelected = !filter.isSelected)
                } else {
                    // Все остальные сбрасываем
                    filter.copy(isSelected = false)
                }
            } as MutableList<Filter>
        }

        // Обновляем selectedFilter_
        val isNowSelected = !newFilter.isSelected // Инвертируем текущее состояние

        selectedFilter_.update {
            if (isNowSelected) {
                // Фильтр стал выбранным
                newFilter.copy(isSelected = true)
            } else {
                // Фильтр стал невыбранным - возвращаем пустой фильтр
                Filter.empty()
            }
        }
    }

    fun getFoodById(id: String): Food? {
        // Ищем в полном списке
        return allFood.value.find { it.id == id }
    }

    companion object {
        class ListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
                    // 1. Создаем БД
                    val db = AvocadoDatabase.getDatabase(context)
                    // 2. Создаем Репозиторий
                    val repo = FavoritesRepository(db.favoriteDao())
                    // 3. Создаем VM
                    @Suppress("UNCHECKED_CAST")
                    return ListViewModel(repo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}