package ru.plumsoftware.avocado.data.shopping

import kotlinx.coroutines.flow.Flow

class ShoppingRepository(private val dao: ShoppingDao) {
    val shoppingList: Flow<List<ShoppingItemEntity>> = dao.getShoppingList()

    suspend fun addItems(items: List<ShoppingItemEntity>) = dao.insertItems(items)
    suspend fun toggleCheck(id: String, isChecked: Boolean) = dao.updateCheckState(id, isChecked)
    suspend fun deleteItem(id: String) = dao.deleteItem(id)
    suspend fun clearChecked() = dao.clearChecked()
}