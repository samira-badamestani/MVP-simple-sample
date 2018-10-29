package simple.mvp.foodlist.data.db

import android.arch.persistence.room.*
import simple.mvp.foodlist.data.model.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: Food): Long

    @Delete
    fun deleteFood(food: Food): Int

    @Query("SELECT * from Food")
    fun selectAllFoods(): MutableList<Food>

    @Query("DELETE FROM Food")
    fun deleteAllFoods()

}