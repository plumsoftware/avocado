package ru.plumsoftware.avocado.data.water

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDao {
    // Получаем воду для конкретной даты
    @Query("SELECT * FROM water_intake WHERE dateString = :date LIMIT 1")
    fun getWaterForDate(date: String): Flow<WaterIntakeEntity?>

    // Вставляем или заменяем (если пользователь пьет воду)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWater(waterIntake: WaterIntakeEntity)
}
