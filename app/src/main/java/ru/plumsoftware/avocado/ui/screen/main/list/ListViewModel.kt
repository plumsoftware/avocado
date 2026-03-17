package ru.plumsoftware.avocado.ui.screen.main.list

import android.content.Context
import android.os.Build
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
import ru.plumsoftware.avocado.data.onboarding.UserRestriction
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.log
import ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.Filter
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodColorCache

class ListViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val context: Context
) : ViewModel() {

    private val filters_ = MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.filters.toMutableList())
    val filters = filters_.asStateFlow()

    private val selectedFilter_ = MutableStateFlow(Filter.empty())
    val selectedFilter = selectedFilter_.asStateFlow()

    // Списки продуктов (как было)
    private val recomendedOnBreakfast_ = MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.recomendedOnBreakfast.toMutableList())
    val recomendedOnBreakfast = recomendedOnBreakfast_.asStateFlow()

    private val withFiber_ = MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.withFiber.toMutableList())
    val withFiber = withFiber_.asStateFlow()

    val heavyProtein_ = MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.top7HighProteinFoods.toMutableList())
    val heavyProtein = heavyProtein_.asStateFlow()

    val healthyFatsItems_ = MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.topOmega3.toMutableList())
    val healthyFatsItems = healthyFatsItems_.asStateFlow()

    val allFood_ = MutableStateFlow(ru.plumsoftware.avocado.data.base.model.food.allFood)
    val allFood = allFood_.asStateFlow()

    // --- ПОИСК ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Flow результатов поиска с задержкой (Debounce)
    @OptIn(FlowPreview::class)
    val searchResults: StateFlow<List<Food>> = _searchQuery
        .debounce(500L)
        .combine(allFood_) { query, foodList ->
            if (query.isBlank()) emptyList()
            else foodList.filter { context.getString(it.titleRes).contains(query, true) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(newQuery: String) { _searchQuery.value = newQuery }

    val favoriteIds: StateFlow<Set<String>> = favoritesRepository.favoriteIds
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val _homeSections = MutableStateFlow<List<HomeSection>>(emptyList())
    val homeSections = _homeSections.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.userGoals,
                userPreferencesRepository.userRestrictions,
                allFood
            ) { goals, restrictions, food ->
                buildHomeSections(goals, restrictions, food)
            }.collect { sections ->
                _homeSections.value = sections
            }
        }
    }

    private val colorCache = FoodColorCache()

    fun onLikeClick(foodId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(foodId)
        }
    }

    fun isFavorite(foodId: String): Boolean {
        return favoriteIds.value.contains(foodId)
    }

    fun getBackgroundColorForFood(imageRes: Int, context: Context): Int {
        return colorCache.getBackgroundColor(imageRes, context)
    }

    fun getTextForColorForFood(imageRes: Int, context: Context): HarmoniousColors {
        return colorCache.getHarmoniousColors(imageRes, context)
    }

    fun updateSelectedFilter(newFilter: Filter) {
        filters_.update { oldList ->
            oldList.map { filter ->
                if (filter.id == newFilter.id) filter.copy(isSelected = !filter.isSelected)
                else filter.copy(isSelected = false)
            } as MutableList<Filter>
        }
        val isNowSelected = !newFilter.isSelected
        selectedFilter_.update {
            if (isNowSelected) newFilter.copy(isSelected = true) else Filter.empty()
        }
    }

    fun getFoodById(id: String): Food? {
        return allFood.value.find { it.id == id }
    }

    private fun buildHomeSections(
        goals: List<UserGoal>,
        restrictions: List<UserRestriction>,
        allFood: List<Food>
    ): List<HomeSection> {

        // 1. ФИЛЬТР БЕЗОПАСНОСТИ (Убираем запрещенку)
        var safeFood = allFood

        if (restrictions.contains(UserRestriction.VEGETARIAN)) {
            safeFood = safeFood.filter { it.foodType != FoodType.MEAT && it.foodType != FoodType.FISH }
        }
        if (restrictions.contains(UserRestriction.VEGAN)) {
            safeFood = safeFood.filter {
                it.foodType != FoodType.MEAT && it.foodType != FoodType.FISH
                // добавь сюда DAIRY/EGGS если есть такие типы
            }
        }
        if (restrictions.contains(UserRestriction.NUT_ALLERGY)) {
            safeFood = safeFood.filter { it.foodType != FoodType.NUT }
        }
        if (restrictions.contains(UserRestriction.SEAFOOD_ALLERGY)) {
            safeFood = safeFood.filter { it.foodType != FoodType.FISH } // и SEAFOOD
        }
        if (restrictions.contains(UserRestriction.NO_SUGAR) || restrictions.contains(UserRestriction.KETO)) {
            safeFood = safeFood.filter { it.kpfc_100g.carbohydrates < 15.0 }
        }

        val sections = mutableListOf<HomeSection>()

        // 2. ЕСЛИ ЦЕЛЕЙ НЕТ -> Дефолтные секции
        if (goals.isEmpty()) {
            sections.add(HomeSection(R.string.for_breakfast, safeFood.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(10)))
            sections.add(HomeSection(R.string.healthy_fats, safeFood.filter { it.kpfc_100g.omega3 > 1.0 }.take(10)))
            return sections
        }

        // 3. ПОДБОР ПОД ЦЕЛИ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            goals.forEach { goal ->
                when (goal) {
                    UserGoal.LOSE_WEIGHT -> {
                        val list = safeFood.filter { it.kpfc_100g.kals < 50 || it.kpfc_100g.fiber > 2.5 }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_weight_loss, list.take(6)))
                    }
                    UserGoal.GAIN_MUSCLE -> {
                        val list = safeFood.filter { it.kpfc_100g.proteins > 12.0 }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_muscle, list.take(6)))
                    }
                    UserGoal.BETTER_SKIN -> {
                        val list = safeFood.filter { f -> f.vitamins.any { it.id == "vitamin_a" || it.id == "vitamin_c" } }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_skin, list.take(6)))
                    }
                    UserGoal.HEART_HEALTH -> {
                        val list = safeFood.filter { f -> f.kpfc_100g.omega3 > 0.5 || f.minerals.any { it.id == "potassium" } }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_heart, list.take(6)))
                    }
                    UserGoal.ENERGY -> {
                        val list = safeFood.filter { f -> f.vitamins.any { it.id.startsWith("vitamin_b") } || f.minerals.any { it.id == "iron" } }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_energy, list.take(6)))
                    }
                    UserGoal.DIGESTION -> {
                        val list = safeFood.filter { it.kpfc_100g.fiber > 3.0 }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_digestion, list.take(6)))
                    }
                    UserGoal.IMMUNITY -> {
                        val list = safeFood.filter { f -> f.vitamins.any { it.id == "vitamin_c" } || f.minerals.any { it.id == "zinc" } }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_immunity, list.take(6)))
                    }
                    UserGoal.SLEEP -> {
                        val list = safeFood.filter { f -> f.minerals.any { it.id == "magnesium" } }
                        if (list.isNotEmpty()) sections.add(HomeSection(R.string.section_sleep, list.take(6)))
                    }
                }
            }
        } else {

        }

        // 4. ЗАПАСНОЙ ВАРИАНТ (Если ничего не подобралось)
        if (sections.isEmpty()) {
            sections.add(HomeSection(R.string.for_breakfast, safeFood.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(10)))
        }

        return sections.distinctBy { it.titleRes }
    }

    // --- ВОДА ---
    val waterIntake: StateFlow<Int> = userPreferencesRepository.waterIntake
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun addWater(amountMl: Int) {
        viewModelScope.launch {
            userPreferencesRepository.addWater(amountMl)
        }
    }

    companion object {
        class ListViewModelFactory(
            private val favoritesRepository: FavoritesRepository,
            private val userPreferencesRepository: UserPreferencesRepository,
            private val context: Context
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ListViewModel(favoritesRepository = favoritesRepository, userPreferencesRepository = userPreferencesRepository, context = context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        // Правильная фабрика для MainActivity
        class Factory(
            private val context: Context,
            private val favoritesRepo: FavoritesRepository,
            private val userPrefsRepo: UserPreferencesRepository
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListViewModel(favoritesRepo, userPrefsRepo, context) as T
            }
        }
    }
}