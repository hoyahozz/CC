package kr.ac.castcommunity.cc

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        Handler().postDelayed({
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }, 1200)
    }
}