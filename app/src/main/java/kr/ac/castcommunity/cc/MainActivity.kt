package kr.ac.castcommunity.cc

import android.os.Bundle
import kotlinx.android.synthetic.main.main.*
import kr.ac.castcommunity.cc.Fragment.BoardListFragment
import kr.ac.castcommunity.cc.Fragment.MainFragment
import kr.ac.castcommunity.cc.Fragment.NoteFragment
import kr.ac.castcommunity.cc.Toolbar.MainToolbar

class MainActivity : MainToolbar() {


    private val fragmentFirst by lazy { MainFragment() }
    private val fragmentSecond by lazy { BoardListFragment() }
    private val fragmentThree by lazy { NoteFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        main_bnv.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.first -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, fragmentFirst)
                            .commit()
                    }

                    R.id.second -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, fragmentSecond)
                            .commit()
                    }

                    R.id.third -> {
                        //changeFragment(fragmentThree)
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, fragmentThree)
                            .commit()
                    }
                }
                true
            }
            selectedItemId = R.id.first
        }


    }



//    private fun changeFragment(fragment: Fragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.main_container, fragment)
//            .commit()
//    }


}
