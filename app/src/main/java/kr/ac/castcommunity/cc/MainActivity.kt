package kr.ac.castcommunity.cc

import ViewPagerAdapter
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.main.*
import kr.ac.castcommunity.cc.Toolbar.MainToolbar

class MainActivity : MainToolbar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)


        main_viewPager.adapter = ViewPagerAdapter(getList())

        var mainboard = main_board
        var nickname = intent.getStringExtra("nickname")

        mainboard.setOnClickListener {
            val intent = Intent(applicationContext, BoardActivity::class.java)
            intent.putExtra("nickname",nickname)
            startActivity(intent)
        }

        useToolbar()
    }
    private fun getList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.exam2,R.drawable.exam3,R.drawable.exam4)
    }

}
