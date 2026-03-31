package ru.plumsoftware.avocado.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.plumsoftware.avocado.data.favorite.FavoriteDao
import ru.plumsoftware.avocado.data.favorite.FavoriteEntity
import ru.plumsoftware.avocado.data.meal.MealPlanDao
import ru.plumsoftware.avocado.data.meal.MealPlanEntity
import ru.plumsoftware.avocado.data.shopping.ShoppingConverters
import ru.plumsoftware.avocado.data.shopping.ShoppingDao
import ru.plumsoftware.avocado.data.shopping.ShoppingItemEntity
import ru.plumsoftware.avocado.data.water.WaterIntakeDao
import ru.plumsoftware.avocado.data.water.WaterIntakeEntity

@Database(
    entities =[
        FavoriteEntity::class,
        ShoppingItemEntity::class,
        MealPlanEntity::class,
        WaterIntakeEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(ShoppingConverters::class)
abstract class AvocadoDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingDao(): ShoppingDao
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun waterIntakeDao(): WaterIntakeDao

    companion object {
        @Volatile
        private var INSTANCE: AvocadoDatabase? = null

        // Старая миграция (v1 -> v2)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `shopping_list` (
                        `foodId` TEXT NOT NULL, 
                        `titleRes` INTEGER NOT NULL, 
                        `foodType` TEXT NOT NULL, 
                        `imageRes` INTEGER NOT NULL, 
                        `isChecked` INTEGER NOT NULL, 
                        PRIMARY KEY(`foodId`)
                    )
                    """.trimIndent()
                )
            }
        }

        // 🔥 НОВАЯ МИГРАЦИЯ (v2 -> v3)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `meal_plan` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `dateString` TEXT NOT NULL, 
                        `mealType` TEXT NOT NULL, 
                        `recipeId` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `water_intake` (
                        `dateString` TEXT NOT NULL, 
                        `amountMl` INTEGER NOT NULL, 
                        PRIMARY KEY(`dateString`)
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): AvocadoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AvocadoDatabase::class.java,
                    "avocado_database"
                )
                    // ПЕРЕДАЕМ ОБЕ МИГРАЦИИ В БИЛДЕР
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}