package ru.plumsoftware.avocado.data.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    // Получаем поток всех ID лайкнутых продуктов
    @Query("SELECT foodId FROM favorites")
    fun getFavoriteIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(item: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE foodId = :id")
    suspend fun removeFromFavorites(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE foodId = :id)")
    suspend fun isFavorite(id: String): Boolean
}