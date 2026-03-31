package ru.plumsoftware.avocado.data.water

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_intake")
data class WaterIntakeEntity(
    @PrimaryKey val dateString: String, // Формат "yyyy-MM-dd"
    val amountMl: Int
)