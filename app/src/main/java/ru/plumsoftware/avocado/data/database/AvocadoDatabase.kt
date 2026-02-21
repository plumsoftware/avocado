package ru.plumsoftware.avocado.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.plumsoftware.avocado.data.favorite.FavoriteDao
import ru.plumsoftware.avocado.data.favorite.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AvocadoDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AvocadoDatabase? = null

        fun getDatabase(context: Context): AvocadoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AvocadoDatabase::class.java,
                    "avocado_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}