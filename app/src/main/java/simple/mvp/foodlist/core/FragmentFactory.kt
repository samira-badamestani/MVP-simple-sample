package simple.mvp.foodlist.core

import android.support.v4.app.FragmentManager
import simple.mvp.foodlist.view.HomeFragment

object FragmentFactory{

    fun getHomeFragment(supportFragmentManager: FragmentManager): HomeFragment {
        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.FRAGMENT_NAME)
        if (fragment == null) {
            fragment = HomeFragment()
        }
        return fragment as HomeFragment
    }

}