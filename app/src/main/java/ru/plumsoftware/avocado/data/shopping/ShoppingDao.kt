package ru.plumsoftware.avocado.data.shopping

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    // Получаем список, отсортированный сначала по статусу (купленные внизу), затем по типу
    @Query("SELECT * FROM shopping_list ORDER BY isChecked ASC, foodType ASC")
    fun getShoppingList(): Flow<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItems(items: List<ShoppingItemEntity>)

    @Query("UPDATE shopping_list SET isChecked = :isChecked WHERE foodId = :id")
    suspend fun updateCheckState(id: String, isChecked: Boolean)

    @Query("DELETE FROM shopping_list WHERE foodId = :id")
    suspend fun deleteItem(id: String)

    @Query("DELETE FROM shopping_list WHERE isChecked = 1")
    suspend fun clearChecked() // Кнопка "Удалить купленное"
}