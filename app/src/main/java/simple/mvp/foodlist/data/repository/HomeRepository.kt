package simple.mvp.foodlist.data.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simple.mvp.foodlist.core.App
import simple.mvp.foodlist.data.db.AppDatabase
import simple.mvp.foodlist.data.model.Food
import simple.mvp.foodlist.data.model.FoodDto
import simple.mvp.foodlist.data.network.ApiService

class HomeRepository {

    private var apiService: ApiService?=null
    private val appDatabase: AppDatabase by lazy { AppDatabase.getInstance() }
    private val TAG: String = HomeRepository::class.java.simpleName

    init {
        apiService= App.api
    }


        fun getFoods(callback: RepositoryCallback<FoodDto>) {
            apiService!!.getHome().enqueue(object : Callback<FoodDto?> {
                override fun onFailure(call: Call<FoodDto?>?, t: Throwable?) {
                    callback.onError("error happened")
                }

                override fun onResponse(call: Call<FoodDto?>?, response: Response<FoodDto?>?) {
                    Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")
                    Log.d(TAG,
                        "isSuccessful : " + response?.isSuccessful
                                + " message : " + response?.message()
                                + " code : " + response?.code()
                                + " raw : " + response?.raw()
                    )

                        if (response != null) {
                            if (response.isSuccessful) {
                                callback.onSuccess(response.body())
                                if (response.body()!!.results.size > 0) {
                                    saveInDB(response.body()!!.results)
                                }

                            } else {
                                callback.onError(response.errorBody()!!.string())
                            }
                        }
                }
            })

        }

     fun saveInDB(results: MutableList<Food>) {
            appDatabase.foodDao().deleteAllFoods()
            for (i in results) {
                Log.d(TAG, "${i.title} inserted to databade")
                appDatabase.foodDao().insertFood(i)
            }
    }

     fun foodsCountInDb() :Int {
        return appDatabase.foodDao().selectAllFoods().size
    }

    interface RepositoryCallback<in T> {
        fun onSuccess(t: T?)
        fun onError(error : String)
    }

}