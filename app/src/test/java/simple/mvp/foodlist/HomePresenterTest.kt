package simple.mvp.foodlist

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import simple.mvp.foodlist.data.model.Food
import simple.mvp.foodlist.data.model.FoodDto
import simple.mvp.foodlist.data.repository.HomeRepository
import simple.mvp.foodlist.view.HomeContract
import simple.mvp.foodlist.view.HomePresenter




class HomePresenterTest{

    private lateinit var presenter : HomePresenter
    private lateinit var view : HomeContract.View
    private lateinit var homeRepository : HomeRepository


    @Before
    fun setup() {
        homeRepository= mock()
        presenter = HomePresenter(homeRepository)
        view = mock()
        presenter.attachView(view)
    }

    @Test
    fun home_callsShowLoading() {
        presenter.getFoods()
        verify(view).showWait()
        verify(homeRepository).getFoods(any())
    }

    @Test
    fun home_withRepositoryHavingFoods_callShowFoods(){
        val foods = mutableListOf<Food>()
        val food = Food(1,"Ginger","http://allrecipes.com/Recipe/Ginger-Champagne/Detail.aspx","champagne","http://img.recipepuppy.com/1.jpg")
        foods.add(food)
        val foodDto = FoodDto(foods)

        doAnswer {
            val callback: HomeRepository.RepositoryCallback<FoodDto> = it.getArgument(0)
            callback.onSuccess(foodDto)
        }.whenever(homeRepository).getFoods(any())

        presenter.getFoods()
        verify(view).showFoods(eq(foodDto))

    }

}