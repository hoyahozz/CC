package kr.ac.castcommunity.cc

import ViewPagerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.note.*
import kotlinx.android.synthetic.main.note.view.*
import kr.ac.castcommunity.cc.Toolbar.MainToolbar

class MainActivity : MainToolbar() {

    private val fragmentThree by lazy { NoteActivity() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        main_viewPager.adapter = ViewPagerAdapter(getList())

        var mainboard = main_board
        mainboard.setOnClickListener {
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }
        useToolbar()

        main_bnv.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
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
        }


    }

    private fun getList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.exam2, R.drawable.exam3, R.drawable.exam4)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }


}
