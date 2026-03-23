package ru.plumsoftware.avocado.ui.screen.main.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.shopping.ShoppingItemEntity
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingList: StateFlow<List<ShoppingItemEntity>> = repository.shoppingList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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