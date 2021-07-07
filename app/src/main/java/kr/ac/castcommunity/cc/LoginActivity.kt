package kr.ac.castcommunity.cc

import LoginRequest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.login.*
import kr.ac.castcommunity.cc.request.WriteRequest
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        var Login = login_button
        var Register = login_join
        var Find = login_find

        Login.setOnClickListener {
            val id = login_id.text.toString()
            val password = login_password.text.toString()

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) {// 로그인에 성공한 경우
                        Toast.makeText(applicationContext, "로그인 성공!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("id",id)
                        intent.putExtra("password",password)
                        startActivity(intent)
                    } else { // 로그인에 실패한 경우
                        Toast.makeText(applicationContext, "로그인 실패!", Toast.LENGTH_LONG).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.

            val loginRequest = LoginRequest(id, password, responseListener)
            val queue = Volley.newRequestQueue(this@LoginActivity)
            queue.add(loginRequest)
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
