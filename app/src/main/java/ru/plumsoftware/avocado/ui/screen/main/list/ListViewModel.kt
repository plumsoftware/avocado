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
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.food.FoodType
import ru.plumsoftware.avocado.data.base.model.food.TimeForFood
import ru.plumsoftware.avocado.data.base.model.food.allFood
import ru.plumsoftware.avocado.data.database.AvocadoDatabase
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.log
import ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.Filter
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodColorCache

class ListViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
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

    private val _homeSections = MutableStateFlow<List<HomeSection>>(emptyList())
    val homeSections = _homeSections.asStateFlow()

    init {
        // Наблюдаем за целями и перестраиваем главную страницу
        viewModelScope.launch {
            userPreferencesRepository.userGoals.collect { goals ->
                _homeSections.value = buildHomeSections(goals, allFood.value)
            }
        }
    }

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

    private fun buildHomeSections(goals: List<UserGoal>, allFood: List<Food>): List<HomeSection> {
        val sections = mutableListOf<HomeSection>()

        // Если целей нет, показываем дефолт
        if (goals.isEmpty()) {
            sections.add(HomeSection(R.string.for_breakfast, allFood.filter { it.timeForFood == TimeForFood.BREAKFAST }))
            sections.add(HomeSection(R.string.healthy_fats, allFood.filter { it.kpfc_100g.omega3 > 1.0 }))
            return sections
        }

        // Генерируем секции под цели
        goals.forEach { goal ->
            when (goal) {
                UserGoal.LOSE_WEIGHT -> {
                    // Низкокалорийные (< 50 ккал) ИЛИ Клетчатка (> 2.5г) - насыщает
                    val dietFood = allFood.filter {
                        it.kpfc_100g.kals < 50 || it.kpfc_100g.fiber > 2.5
                    }
                    sections.add(HomeSection(R.string.section_weight_loss, dietFood.take(6)))
                }

                UserGoal.GAIN_MUSCLE -> {
                    // Много белка (> 12г)
                    val proteinFood = allFood.filter {
                        it.kpfc_100g.proteins > 12.0
                    }
                    sections.add(HomeSection(R.string.section_muscle, proteinFood.take(6)))
                }

                UserGoal.BETTER_SKIN -> {
                    // Витамин A, E, C (Антиоксиданты) + Омега-3
                    val skinFood = allFood.filter { food ->
                        food.vitamins.any { it.id == "vitamin_a" || it.id == "vitamin_e" || it.id == "vitamin_c" } ||
                                food.kpfc_100g.omega3 > 0.5
                    }
                    sections.add(HomeSection(R.string.section_skin, skinFood.take(6)))
                }

                UserGoal.HEART_HEALTH -> {
                    // Омега-3, Магний, Калий
                    val heartFood = allFood.filter { food ->
                        food.kpfc_100g.omega3 > 0.5 ||
                                food.minerals.any { it.id == "potassium" || it.id == "magnesium" }
                    }
                    sections.add(HomeSection(R.string.section_heart, heartFood.take(6)))
                }

                UserGoal.ENERGY -> {
                    // Железо, Витамины группы B (B1, B2, B6, B12), Медленные углеводы
                    val energyFood = allFood.filter { food ->
                        food.minerals.any { it.id == "iron" } ||
                                food.vitamins.any { it.id.startsWith("vitamin_b") } ||
                                (food.kpfc_100g.carbohydrates > 10.0 && food.kpfc_100g.fiber > 1.0) // Сложные углеводы
                    }
                    sections.add(HomeSection(R.string.section_energy, energyFood.take(6)))
                }

                UserGoal.DIGESTION -> {
                    // Высокая клетчатка (> 3г) или легкие овощи
                    val digestionFood = allFood.filter { food ->
                        food.kpfc_100g.fiber > 3.0 || food.foodType == FoodType.VEGETABLE
                    }
                    sections.add(HomeSection(R.string.section_digestion, digestionFood.take(6)))
                }

                UserGoal.IMMUNITY -> {
                    // Витамин C, D, Цинк, Селен
                    val immunityFood = allFood.filter { food ->
                        food.vitamins.any { it.id == "vitamin_c" || it.id == "vitamin_d" } ||
                                food.minerals.any { it.id == "zinc" || it.id == "selenium" }
                    }
                    sections.add(HomeSection(R.string.section_immunity, immunityFood.take(6)))
                }

                UserGoal.SLEEP -> {
                    // Магний, B6 (помогают выработке мелатонина), Кальций
                    val sleepFood = allFood.filter { food ->
                        food.minerals.any { it.id == "magnesium" || it.id == "calcium" } ||
                                food.vitamins.any { it.id == "vitamin_b6" }
                    }
                    sections.add(HomeSection(R.string.section_sleep, sleepFood.take(6)))
                }
            }
        }

        // Всегда добавляем "Для завтрака" в конце, если не было
        sections.add(HomeSection(R.string.for_breakfast, allFood.filter { it.timeForFood == TimeForFood.BREAKFAST }))

        return sections.distinctBy { it.titleRes } // Убираем дубли
    }

    companion object {
        class ListViewModelFactory(
            private val favoritesRepository: FavoritesRepository,
            private val userPreferencesRepository: UserPreferencesRepository
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ListViewModel(favoritesRepository = favoritesRepository, userPreferencesRepository = userPreferencesRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}