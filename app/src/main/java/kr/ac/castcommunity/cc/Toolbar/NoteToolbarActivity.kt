package kr.ac.castcommunity.cc.Toolbar

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.ac.castcommunity.cc.BoardActivity
import kr.ac.castcommunity.cc.NoteActivity
import kr.ac.castcommunity.cc.R

open class NoteToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_toolbar)

    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.note_toolbar, null) as LinearLayout
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
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, NoteActivity::class.java)
                startActivity(intent)
                return true;
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}