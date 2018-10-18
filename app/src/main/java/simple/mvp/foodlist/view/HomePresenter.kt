package simple.mvp.foodlist.view

import android.util.Log
import simple.mvp.foodlist.data.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simple.mvp.foodlist.data.db.AppDatabase
import simple.mvp.foodlist.data.model.Food
import simple.mvp.foodlist.data.model.FoodDto

class HomePresenter (val apiService: ApiService,
                     val appDatabase: AppDatabase) : HomeContract.Presenter{

    var homeView: HomeContract.View?=null

    private val TAG: String = HomePresenter::class.java.simpleName
    override fun getFoods() {
        if (homeView!=null) {
            homeView!!.showWait()
        }
        apiService.getHome().enqueue(object : Callback<FoodDto?> {
            override fun onFailure(call: Call<FoodDto?>?, t: Throwable?) {
                if (homeView!=null) {
                    homeView!!.removeWait()
                    homeView!!.onFailure("error happened")
                }
            }

            override fun onResponse(call: Call<FoodDto?>?, response: Response<FoodDto?>?) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")
                Log.d(TAG,
                    "isSuccessful : " + response?.isSuccessful
                            + " message : " + response?.message()
                            + " code : " + response?.code()
                            + " raw : " + response?.raw()
                )

                if (homeView!=null) {
                    homeView!!.removeWait()

                    if (response != null) {
                        if (response.isSuccessful) {
                            homeView!!.showFoods(response.body()!!)
                            if (response.body()!!.results.size > 0) {
                                saveInDB(response.body()!!.results)
                            }

                        } else {
                            homeView!!.onFailure(response.errorBody()!!.string())
                        }
                    }
                }
            }
        })

}
    override fun attachView(view: HomeContract.View) {
        homeView=view
    }

    override fun detachView() {
        homeView=null
    }

    private fun saveInDB(results: MutableList<Food>) {
        if (homeView!=null) {
            for (i in results) {
                Log.d(TAG, "${i.title} inserted to databade")
                appDatabase.foodDao().insertFood(i)
            }
        }
    }
}
