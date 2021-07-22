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

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null

    override fun onBackPressed() { // 뒤로가기 금지
        // super.onBackPressed()

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast?.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast?.cancel();
        }
    }

}
