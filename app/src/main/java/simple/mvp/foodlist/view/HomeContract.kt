package simple.mvp.foodlist.view

import simple.mvp.foodlist.data.model.FoodDto

interface HomeContract {
    interface View  {
        fun showWait()
        fun removeWait()
        fun showFoods(foodDto: FoodDto)
        fun onFailure(message: String)
    }

    interface Presenter{
        fun getFoods()
        fun attachView(view: View)
        fun detachView()
    }
}