package simple.mvp.foodlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar_main.*
import simple.mvp.foodlist.core.FragmentFactory
import simple.mvp.foodlist.view.HomeFragment

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar by lazy { toolbar_main_activity }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        showHomeFragment()
    }

    private fun showHomeFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                FragmentFactory.getHomeFragment(supportFragmentManager),
                HomeFragment.FRAGMENT_NAME
            )
        fragmentTransaction.addToBackStack(HomeFragment.FRAGMENT_NAME)
        fragmentTransaction.commit()
    }
}
