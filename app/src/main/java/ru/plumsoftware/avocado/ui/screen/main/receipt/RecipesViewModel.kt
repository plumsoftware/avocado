package ru.plumsoftware.avocado.ui.screen.main.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.receipt.TypicalReceipt
import kotlin.collections.take

class RecipesViewModel : ViewModel() {

    // --- ДАННЫЕ (MOCK DATA) ---
    // В реальности это должно приходить из Repository
    private val allReceipts = ru.plumsoftware.avocado.data.base.model.receipt.allReceipts

    // --- STATE FLOWS ---

    // 1. Категории фильтров
    private val _categories = MutableStateFlow(listOf("Все", "Завтрак", "Обед", "Ужин", "Салаты", "Смузи"))
    val categories = _categories.asStateFlow()

    // 2. Выбранная категория
    private val _selectedCategory = MutableStateFlow("Все")
    val selectedCategory = _selectedCategory.asStateFlow()

    // 3. Рецепты для баннера (Featured)
    private val _featuredReceipts = MutableStateFlow(allReceipts.take(3)) // Берем первые 3 или случайные
    val featuredReceipts = _featuredReceipts.asStateFlow()

    // 4. Основной список (Фильтрованный)
    val recipeList: StateFlow<List<TypicalReceipt>> = _selectedCategory
        .combine(MutableStateFlow(allReceipts)) { category, receipts ->
            if (category == "Все") {
                receipts
            } else {
                receipts.filter { it.category == category }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = allReceipts
        )

    // --- EVENTS ---

    fun onCategorySelect(category: String) {
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

    // Фабрика
    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipesViewModel() as T
        }
    }
}