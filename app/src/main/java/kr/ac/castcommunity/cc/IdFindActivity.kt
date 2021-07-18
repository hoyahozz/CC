package kr.ac.castcommunity.cc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.idfind.*
import kotlinx.android.synthetic.main.pwfind.*
import kotlinx.android.synthetic.main.register.*
import kr.ac.castcommunity.cc.Toolbar.FindToolbarActivity
import kr.ac.castcommunity.cc.request.IdFindRequest
import kr.ac.castcommunity.cc.request.PwFindRequest
import kr.ac.castcommunity.cc.request.RegisterRequest
import org.json.JSONException
import org.json.JSONObject

class IdFindActivity : FindToolbarActivity() {
    private var dialog: android.app.AlertDialog? = null
    private var validate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.idfind)

        val pwfind = findViewById<Button>(R.id.findTo_pw)
        //비밀번호 찾기 클릭시 이동
        pwfind.setOnClickListener {
            val intent = Intent(applicationContext, PwFindActivity::class.java)
            startActivity(intent)
        }


//        //아이디 찾기 클릭시
        id_find.setOnClickListener {
            val name = name?.text.toString()
            val email = email_find?.text.toString()
            if (validate) {
                return@setOnClickListener
            }
            //빈칸이 있을경우
            if (name.equals("") || email.equals("")) {
                val builder: android.app.AlertDialog.Builder =
                    android.app.AlertDialog.Builder(this@IdFindActivity)
                dialog = builder.setMessage("정보를 모두 입력해주세요").setNegativeButton("확인", null).create()
                dialog!!.show()
                return@setOnClickListener
            }
            //아이디 찾기 진행
            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response) // JSON 형태로 결과값을 받아온다.
                    val success: Boolean =
                        jsonObject.getBoolean("success") // 조건에 맞는 값이 있으면 PHP에서 'success' 를 true로 리턴한다.
                    if (success) { // 조건에 맞는 값이 있을 경우
                        val id: String = jsonObject.getString("id") // JSON 에서 조건에 맞는 id값을 찾아와 저장한다.
                        validate = true // 조건에 부합하니 true 설정
                        val builder = android.app.AlertDialog.Builder(this@IdFindActivity)
                        builder.setMessage("아이디는 " + id + " 입니다").setPositiveButton("확인", null)
                            .create()
                        builder.show()

                    } else { // 조건에 맞는 값이 없을 경우
                        val builder = android.app.AlertDialog.Builder(this@IdFindActivity)
                        builder.setMessage("일치하는 정보가 없습니다").setPositiveButton("확인", null)
                        builder.show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            // Volley 라이브러리를 이용해 실제 서버와 통신을 구현하는 부분
            val idFindRequest = IdFindRequest(name, email, responseListener)
            val queue = Volley.newRequestQueue(this@IdFindActivity)
            queue.add(idFindRequest)
        }
    }
}
