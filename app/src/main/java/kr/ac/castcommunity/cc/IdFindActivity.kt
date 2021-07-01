package kr.ac.castcommunity.cc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IdFindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.idfind)

        var pwFind = findViewById<Button>(R.id.findTo_pw)

        pwFind.setOnClickListener {
        val intent = Intent(applicationContext, PwFindActivity::class.java)
        startActivity(intent)
        }
    }
}
