package kr.ac.castcommunity.cc.Toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import kr.ac.castcommunity.cc.BoardActivity
import kr.ac.castcommunity.cc.LoginActivity
import kr.ac.castcommunity.cc.MainActivity
import kr.ac.castcommunity.cc.R

open class MyInfoToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myinfo_toolbar)
        // 액션바에 표시되는 제목의 표시 유무 설정(False -> 이름이 화면에 보이게 됨)
    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.myinfo_toolbar, null) as LinearLayout
        val activityContainer = fullView.findViewById<View>(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        val toolbar =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.myinfo_toolbar) //툴바 사용여부 결정(기본적으로 사용)
        if (useToolbar()) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()!!.setHomeAsUpIndicator(R.drawable.leftback)

        } else {
            toolbar.visibility = View.GONE
        }
    }

    protected fun useToolbar(): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return true;
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
