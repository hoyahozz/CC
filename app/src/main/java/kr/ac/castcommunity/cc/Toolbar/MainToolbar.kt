package kr.ac.castcommunity.cc.Toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import kr.ac.castcommunity.cc.LoginActivity
import kr.ac.castcommunity.cc.MyInfoActivity
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.WriteActivity

open class MainToolbar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_toolbar)
        // 액션바에 표시되는 제목의 표시 유무 설정(False -> 이름이 화면에 보이게 됨)
    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.main_toolbar, null) as LinearLayout
        val activityContainer = fullView.findViewById<View>(R.id.main_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        val toolbar =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar) //툴바 사용여부 결정(기본적으로 사용)
        if (useToolbar()) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        } else {
            toolbar.visibility = View.GONE
        }
    }

    protected fun useToolbar(): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.main_search -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.main_people -> {
                //내 정보 눌렀을 때
                val intent = Intent(applicationContext, MyInfoActivity::class.java)
                startActivity(intent)
                return true;
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
