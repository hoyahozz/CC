package kr.ac.castcommunity.cc.Board

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
import kr.ac.castcommunity.cc.R

open class BoardToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_toolbar)
    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.board_toolbar, null) as LinearLayout
        val activityContainer = fullView.findViewById<View>(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.board_toolbar) //툴바 사용여부 결정(기본적으로 사용)
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
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.action_search -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_menu -> {
                //공유 버튼 눌렀을 때
                Toast.makeText(applicationContext, "공유 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            android.R.id.home -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                return true;
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
