package simple.mvp.foodlist.data.network



import retrofit2.Call
import retrofit2.http.GET
import simple.mvp.foodlist.data.model.FoodDto

interface ApiService {

    @GET("api/")
    fun getHome(
    ): Call<FoodDto>


}