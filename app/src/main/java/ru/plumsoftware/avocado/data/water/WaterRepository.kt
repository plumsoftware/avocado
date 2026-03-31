package ru.plumsoftware.avocado.data.water

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WaterRepository(private val dao: WaterIntakeDao) {
    // Возвращаем просто Int (миллилитры), если записи нет - возвращаем 0
    fun getWaterForDate(dateString: String): Flow<Int> {
        return dao.getWaterForDate(dateString).map { it?.amountMl ?: 0 }
    }

    suspend fun addWater(dateString: String, currentAmount: Int, addedAmount: Int) {
        val newTotal = currentAmount + addedAmount
        dao.insertWater(WaterIntakeEntity(dateString, newTotal))
    }
}