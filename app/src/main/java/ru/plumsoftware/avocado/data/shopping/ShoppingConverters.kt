package ru.plumsoftware.avocado.data.shopping

import androidx.room.TypeConverter
import ru.plumsoftware.avocado.data.base.model.food.FoodType

class ShoppingConverters {
    @TypeConverter
    fun fromFoodType(value: FoodType): String = value.name

    @TypeConverter
    fun toFoodType(value: String): FoodType = enumValueOf(value)
}