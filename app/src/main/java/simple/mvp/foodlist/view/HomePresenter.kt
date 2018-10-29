package simple.mvp.foodlist.view


import simple.mvp.foodlist.data.model.FoodDto
import simple.mvp.foodlist.data.repository.HomeRepository

class HomePresenter (private val homeRepository: HomeRepository) : HomeContract.Presenter{

    var homeView: HomeContract.View?=null
    private val TAG: String = HomePresenter::class.java.simpleName

    override fun getFoods() {
        if (homeView!=null) {
            homeView!!.showWait()
        }
        homeRepository.getFoods(object : HomeRepository.RepositoryCallback<FoodDto> {
            override fun onSuccess(foodDto: FoodDto?) {
                if (homeView!=null) {
                    homeView!!.removeWait()
                    homeView!!.showFoods(foodDto!!)
                }

            }

            override fun onError(error: String) {
                if (homeView!=null) {
                    homeView!!.removeWait()
                    homeView!!.onFailure(error)
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


}
