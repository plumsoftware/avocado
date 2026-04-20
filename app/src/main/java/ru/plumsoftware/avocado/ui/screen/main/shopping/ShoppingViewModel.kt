package ru.plumsoftware.avocado.ui.screen.main.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.base.model.food.FoodType
import ru.plumsoftware.avocado.data.shopping.ShoppingItemEntity
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    // Сырой список (как было)
    val shoppingList: StateFlow<List<ShoppingItemEntity>> = repository.shoppingList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 🔥 НОВОЕ: Сгруппированный список для Умной Корзины
    // Мы группируем продукты по FoodType и сортируем категории по алфавиту/порядку
    val groupedShoppingList: StateFlow<Map<FoodType, List<ShoppingItemEntity>>> = repository.shoppingList
        .map { list ->
            list.groupBy { it.foodType }.toSortedMap(compareBy { it.name })
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun toggleCheck(id: String, isChecked: Boolean) {
        viewModelScope.launch { repository.toggleCheck(id, isChecked) }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch { repository.deleteItem(id) }
    }

    fun clearChecked() {
        viewModelScope.launch { repository.clearChecked() }
    }

    class Factory(private val repo: ShoppingRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ShoppingViewModel(repo) as T
        }
    }
}