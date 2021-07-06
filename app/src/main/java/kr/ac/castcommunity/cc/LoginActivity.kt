package kr.ac.castcommunity.cc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        var Login = login_button
        var Register = login_join
        var Find = login_find

        Login.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        Register.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

        Find.setOnClickListener {
            val intent = Intent(applicationContext, IdFindActivity::class.java)
            startActivity(intent)
        }
    }
}
