package simple.mvp.foodlist.data.model

import com.google.gson.annotations.SerializedName

data class FoodDto(
    @SerializedName("results") val results: MutableList<Food>
)