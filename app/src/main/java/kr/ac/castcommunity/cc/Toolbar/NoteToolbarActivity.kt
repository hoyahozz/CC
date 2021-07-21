package kr.ac.castcommunity.cc.Toolbar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kr.ac.castcommunity.cc.MainActivity
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.NoteSendActivity

open class NoteToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_detail_toolbar)

    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.note_detail_toolbar, null) as LinearLayout
        val activityContainer = fullView.findViewById<View>(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        //Toolbar 사용여부 결정

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.note_toolbar)
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
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_send -> {
                //보내기 버튼 눌렀을 때
                val intent = Intent(applicationContext, NoteSendActivity ::class.java)
                startActivity(intent)
                return true
            }
//            R.id.action_menu -> {
//                //메뉴 버튼 눌렀을 때
//                val intent = Intent(applicationContext, Activity::class.java)
//                startActivity(intent)
//                return true
//            }
            android.R.id.home -> {
                finish()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}