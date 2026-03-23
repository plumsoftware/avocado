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
import ru.plumsoftware.avocado.data.shopping.ShoppingConverters
import ru.plumsoftware.avocado.data.shopping.ShoppingDao
import ru.plumsoftware.avocado.data.shopping.ShoppingItemEntity

@Database(
    entities = [FavoriteEntity::class, ShoppingItemEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ShoppingConverters::class)
abstract class AvocadoDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var INSTANCE: AvocadoDatabase? = null

        // 2. ПИШЕМ МИГРАЦИЮ (Что делать при переходе с v1 на v2)
        // Нам нужно просто создать новую таблицу. Старая (favorites) останется нетронутой.
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // SQLite синтаксис для создания таблицы.
                // Boolean в SQLite хранится как INTEGER (0 - false, 1 - true).
                // Enum (FoodType) Room по умолчанию сохраняет как TEXT (строку с названием).
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

        fun getDatabase(context: Context): AvocadoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AvocadoDatabase::class.java,
                    "avocado_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}