package kr.ac.castcommunity.cc

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.main.*
import kr.ac.castcommunity.cc.Fragment.BoardListFragment
import kr.ac.castcommunity.cc.Fragment.MainFragment
import kr.ac.castcommunity.cc.Fragment.MessageFragment
import kr.ac.castcommunity.cc.Toolbar.MainToolbarActivity

class MainActivity : MainToolbarActivity() {


    private val fragmentFirst by lazy { MainFragment() }
    private val fragmentSecond by lazy { BoardListFragment() }
    private val fragmentThree by lazy { MessageFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val number = intent.getIntExtra("number",0)

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
            if (number == 3) {
                selectedItemId = R.id.third
            } else {
                selectedItemId = R.id.first
            }
        }


    }

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null

    override fun onBackPressed() { // ???????????? ??????
        // super.onBackPressed()

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ???????????????.", Toast.LENGTH_SHORT);
            toast?.show();
            return;
        }
        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ?????? ??????????????? ?????? ???
        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ????????? ???????????? ??????
        // ?????? ????????? Toast ??????
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast?.cancel();
        }
    }

}
