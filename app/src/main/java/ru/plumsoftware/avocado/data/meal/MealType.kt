package ru.plumsoftware.avocado.data.meal

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK
}

@Entity(tableName = "meal_plan")
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateString: String, // Формат: "yyyy-MM-dd"
    val mealType: String,   // Название из Enum: "BREAKFAST"
    val recipeId: String    // ID рецепта, например "avocado_toast"
)