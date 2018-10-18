package simple.mvp.foodlist.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import simple.mvp.foodlist.core.App
import simple.mvp.foodlist.data.model.Food

@Database(entities = [Food::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    companion object {
        const val DB_NAME = "food.db"
        const val VERSION = 1
        private val instance: AppDatabase  by lazy { create(App.instance) }

        @Synchronized
        internal fun getInstance(): AppDatabase {
            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

            }
    }

