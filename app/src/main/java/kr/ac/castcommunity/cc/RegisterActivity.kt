package kr.ac.castcommunity.cc

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.register.*
import kr.ac.castcommunity.cc.Toolbar.RegisterToolbarActivity
import kr.ac.castcommunity.cc.Request.NickValidateRequest
import kr.ac.castcommunity.cc.Request.RegisterRequest
import kr.ac.castcommunity.cc.Request.ValidateRequest
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : RegisterToolbarActivity() {

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        var check = overlap_check
        var role = "" // 신분 초기값

        join_rdGroup.setOnCheckedChangeListener { // 라디오 그룹이 변경될 때마다 신분이 변경됨
                group, checkedId ->
            when (checkedId) {
                R.id.join_rdButton1 -> role = "Cast"
                R.id.join_rdButton2 -> role = "Normal"
            }
        }

        //닉네임 중복 확인
        nickname_chk.setOnClickListener {
            val nickname = join_nickname.text.toString()
            if (nickname!!.equals("")) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
                dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create()
                dialog!!.show()
                return@setOnClickListener
            } else {
                val responseListener: Response.Listener<String> =
                    Response.Listener<String> { response ->
                        try {
                            Log.d("nickname", join_nickname.toString())
                            Log.d("response", "start")
                            val jsonResponse = JSONObject(response)
                            val success: Boolean = jsonResponse.getBoolean("success")
                            if (success) {
                                val builder: AlertDialog.Builder =
                                    AlertDialog.Builder(this@RegisterActivity)
                                dialog = builder.setMessage("사용 가능한 닉네임입니다.")
                                    .setPositiveButton("확인", null).create()
                                dialog!!.show()
                            } else {
                                val builder: AlertDialog.Builder =
                                    AlertDialog.Builder(this@RegisterActivity)
                                dialog =
                                    builder.setMessage("이미 존재하는 닉네임입니다.")
                                        .setNegativeButton("확인", null).create()
                                dialog!!.show()

                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                val nickValidateRequest = NickValidateRequest(nickname, responseListener)
                val queue: RequestQueue = Volley.newRequestQueue(this@RegisterActivity)
                queue.add(nickValidateRequest)
            }
        }
        // 아이디 중복 체크
        check.setOnClickListener { view ->
            var id = join_id?.text.toString()
            if (id!!.equals("")) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
                dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create()
                dialog!!.show()
                return@setOnClickListener
            }
            val responseListener: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        Log.d("userid", id.toString())
                        Log.d("response", "start")
                        val jsonResponse = JSONObject(response)
                        val success: Boolean = jsonResponse.getBoolean("success")

                        if (success) {
                            val builder: AlertDialog.Builder =
                                AlertDialog.Builder(this@RegisterActivity)
                            dialog =
                                builder.setMessage("사용 가능한 아이디입니다.").setPositiveButton("확인", null)
                                    .create()
                            dialog!!.show()
                        } else {
                            val builder: AlertDialog.Builder =
                                AlertDialog.Builder(this@RegisterActivity)
                            dialog = builder.setMessage("이미 존재하는 아이디입니다.")
                                .setNegativeButton("확인", null).create()
                            dialog!!.show()

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            val validateRequest = ValidateRequest(id, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this@RegisterActivity)
            queue.add(validateRequest)
        }


        //회원가입(join_button)버튼 클릭 시 수행
        join_button?.setOnClickListener {
            val id = join_id?.getText().toString()
            val password = join_password?.text.toString()
            val password2 = join_password2?.text.toString()
            val name = join_name?.text.toString()
            val nickname = join_nickname?.text.toString()
            val email = join_email?.text.toString()

            //빈칸이 있을경우
            if (id.equals("") || password.equals("") || password2.equals("") || name.equals("") ||
                nickname.equals("") || email.equals("") || role.equals("")
            ) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
                dialog = builder.setMessage("정보를 모두 입력해주세요").setNegativeButton("확인", null).create()
                dialog!!.show()
                return@setOnClickListener
            }

            //회원가입 진행
            if (password.equals(password2)) {
                val responseListener = Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")

                        //비밀번호가 같을 경우

                        if (success == true) {// 회원가입 성공한 경우
                            finish()
                        } else { // 회원가입 실패한 경우
                            Toast.makeText(applicationContext, "회원가입 실패", Toast.LENGTH_LONG)
                                .show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                // Volley 라이브러리를 이용해 실제 서버와 통신을 구현하는 부분
                val registerRequest =
                    RegisterRequest(id, password, name, nickname, email, role, responseListener)
                val queue = Volley.newRequestQueue(this@RegisterActivity)
                queue.add(registerRequest)
            }

            else { //비밀번호 입력이 다를경우
                val builder: AlertDialog.Builder =
                    AlertDialog.Builder(this@RegisterActivity)
                dialog =
                    builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null)
                        .create()
                dialog!!.show()
            }
        }
    }
}