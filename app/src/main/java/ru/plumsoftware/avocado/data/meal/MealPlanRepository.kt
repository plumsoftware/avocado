package ru.plumsoftware.avocado.data.meal

import kotlinx.coroutines.flow.Flow

class MealPlanRepository(private val dao: MealPlanDao) {
    fun getPlanForDate(dateString: String): Flow<List<MealPlanEntity>> = dao.getPlanForDate(dateString)

    suspend fun addMeal(dateString: String, mealType: MealType, recipeId: String) {
        dao.insertMeal(MealPlanEntity(dateString = dateString, mealType = mealType.name, recipeId = recipeId))
    }

    suspend fun deleteMeal(id: Int) {
        dao.deleteMeal(id)
    }
}