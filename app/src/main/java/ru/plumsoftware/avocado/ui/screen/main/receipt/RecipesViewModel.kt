package ru.plumsoftware.avocado.ui.screen.main.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.receipt.RecipeCategory
import ru.plumsoftware.avocado.data.base.model.receipt.TypicalReceipt
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.data.shopping.ShoppingItemEntity
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import kotlin.collections.take

class RecipesViewModel(
    userPreferencesRepository: UserPreferencesRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    // --- ДАННЫЕ
    private val allReceipts = ru.plumsoftware.avocado.data.base.model.receipt.allReceipts
    val allRecipesList = allReceipts

    // --- STATE FLOWS ---

    val userGoals: StateFlow<List<UserGoal>> = userPreferencesRepository.userGoals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 1. Категории фильтров
    private val _categories = MutableStateFlow(RecipeCategory.entries)
    val categories = _categories.asStateFlow()

    // 2. Выбранная категория: По умолчанию ALL
    private val _selectedCategory = MutableStateFlow(RecipeCategory.ALL)
    val selectedCategory = _selectedCategory.asStateFlow()

    // 3. Featured (Баннер)
    private val _featuredReceipts = MutableStateFlow(allReceipts.take(5))
    val featuredReceipts: StateFlow<List<TypicalReceipt>> = userGoals.map { goals ->
        if (goals.isEmpty()) {
            allReceipts.shuffled().take(7) // Если целей нет - просто случайные
        } else {
            // Сортируем: чем больше совпадений по целям, тем выше рецепт
            allReceipts.sortedByDescending { recipe ->
                recipe.suitableGoals.count { goal -> goals.contains(goal) }
            }.take(7)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allReceipts.take(7))

    // 4. Основной список (Фильтрованный)
    val recipeList: StateFlow<List<TypicalReceipt>> = _selectedCategory
        .combine(MutableStateFlow(allReceipts)) { category, receipts ->
            if (category == RecipeCategory.ALL) receipts else receipts.filter { it.category == category }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allReceipts)

    // --- EVENTS ---

    fun onCategorySelect(category: RecipeCategory) {
        _selectedCategory.value = category
    }
    // Внутри RecipesViewModel

    // Получить рецепт по ID
    fun getReceiptById(id: String): TypicalReceipt? {
        return allReceipts.find { it.id == id }
    }

    // Получить список объектов Food по списку ID (relatedFood)
    fun getIngredients(ids: List<String>): List<Food> {
        // Берем глобальный список allFood (предполагаем, что он доступен)
        // В реальном app это должно быть через Repository, но пока берем из статики
        return ru.plumsoftware.avocado.data.base.model.food.allFood.filter { it.id in ids }
    }

    fun addIngredientsToCart(foods: List<Food>) {
        viewModelScope.launch {
            val entities = foods.map {
                ShoppingItemEntity(
                    foodId = it.id,
                    titleRes = it.titleRes,
                    foodType = it.foodType,
                    imageRes = it.imageRes
                )
            }
            shoppingRepository.addItems(entities)
        }
    }

    // Фабрика
    class Factory(private val userPrefsRepo: UserPreferencesRepository, private val shoppingRepository: ShoppingRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipesViewModel(userPreferencesRepository = userPrefsRepo, shoppingRepository = shoppingRepository) as T
        }
    }
}