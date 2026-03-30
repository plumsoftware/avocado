package ru.plumsoftware.avocado.data.meal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM meal_plan WHERE dateString = :date ORDER BY id ASC")
    fun getPlanForDate(date: String): Flow<List<MealPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealPlanEntity)

    @Query("DELETE FROM meal_plan WHERE id = :id")
    suspend fun deleteMeal(id: Int)
}