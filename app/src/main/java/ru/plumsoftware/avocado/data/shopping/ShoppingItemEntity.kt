package ru.plumsoftware.avocado.data.shopping

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.plumsoftware.avocado.data.base.model.food.FoodType

@Entity(tableName = "shopping_list")
data class ShoppingItemEntity(
    @PrimaryKey val foodId: String,
    val titleRes: Int,       // Название ресурса для перевода
    val foodType: FoodType,  // Для умной группировки
    val imageRes: Int,       // Чтобы список был красивым
    val isChecked: Boolean = false
)