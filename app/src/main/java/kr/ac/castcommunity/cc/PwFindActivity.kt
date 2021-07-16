package kr.ac.castcommunity.cc

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.pwfind.*
import kr.ac.castcommunity.cc.Toolbar.FindToolbarActivity
import kr.ac.castcommunity.cc.request.PwFindRequest
import kr.ac.castcommunity.cc.request.RegisterRequest
import org.json.JSONException
import org.json.JSONObject

class PwFindActivity : FindToolbarActivity() {
    private var dialog: AlertDialog? = null
    private var validate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pwfind)
        val idFind = findViewById<Button>(R.id.findTo_id)
        //아이디 찾기 클릭으로 이동
        idFind.setOnClickListener {
            val intent = Intent(applicationContext, IdFindActivity::class.java)
            startActivity(intent)
        }
        //비밀번호 찾기 클릭시
        pwbtn.setOnClickListener {
            val id = id?.text.toString()
            val email = email?.text.toString()
            if (validate) {
                return@setOnClickListener
            }
            //빈칸이 있을경우
            if (id.equals("") || email.equals("")) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@PwFindActivity)
                dialog = builder.setMessage("정보를 모두 입력해주세요").setNegativeButton("확인", null).create()
                dialog!!.show()
                return@setOnClickListener
            }
            //비밀번호 찾기 진행
            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response) // JSON 형태로 결과값을 받아온다.
                    val success: Boolean = jsonObject.getBoolean("success") // 조건에 맞는 값이 있으면 PHP에서 'success' 를 true로 리턴한다.
                    if (success) { // 조건에 맞는 값이 있을 경우
                        val pw: String = jsonObject.getString("password") // JSON 에서 조건에 맞는 password값을 찾아와 저장한다.
                        validate = true // 조건에 부합하니 true 설정
                        val builder = AlertDialog.Builder(this@PwFindActivity)
                        builder.setMessage("비밀번호는 " + pw + " 입니다").setPositiveButton("확인", null).create()
                        builder.show()

                    } else { // 조건에 맞는 값이 없을 경우
                        val builder = AlertDialog.Builder(this@PwFindActivity)
                        builder.setMessage("일치하는 정보가 없습니다").setPositiveButton("확인", null)
                        builder.show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            // Volley 라이브러리를 이용해 실제 서버와 통신을 구현하는 부분
            val pwFindRequest = PwFindRequest(id, email, responseListener)
            val queue = Volley.newRequestQueue(this@PwFindActivity)
            queue.add(pwFindRequest)
        }
    }
}
