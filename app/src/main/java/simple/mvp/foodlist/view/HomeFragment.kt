package simple.mvp.foodlist.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.*
import simple.mvp.foodlist.R
import simple.mvp.foodlist.data.model.FoodDto
import simple.mvp.foodlist.data.repository.HomeRepository


class  HomeFragment: Fragment(),HomeContract.View {

    private val TAG: String = HomeFragment::class.java.simpleName
    private var presenter: HomePresenter? = null

    companion object {
        val FRAGMENT_NAME: String = HomeFragment::class.java.name
    }

    val adapter : HomeAdapter by lazy { HomeAdapter(arrayListOf()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter=HomePresenter(HomeRepository())
        presenter!!.attachView(this)
        presenter!!.getFoods()

    }

    override fun showWait() {
        progressBar_home.visibility=View.VISIBLE
    }

    override fun removeWait() {
        progressBar_home.visibility=View.GONE
    }

    override fun showFoods(foodDto: FoodDto) {
        rv_main_home.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_main_home.adapter = adapter
        if (foodDto.results.isNotEmpty()) {
            adapter.clear()
            adapter.add(foodDto.results)

        }else{
            Toast.makeText(context, context?.getString(R.string.empty_list), android.widget.Toast.LENGTH_LONG).show()
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(context,message, android.widget.Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter!!.detachView()
    }
}