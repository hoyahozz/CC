package kr.ac.castcommunity.cc

import LoginRequest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.pwchange.*
import kr.ac.castcommunity.cc.request.PwChangeRequest
import org.json.JSONException
import org.json.JSONObject

class PwChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pwchange)
        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE) // 초기화
        val userID = pref.getString("id", "").toString() // 저장한 값 불러오는 과정
        val my_pw = pref.getString("pw", "").toString()
        val builder = AlertDialog.Builder(this@PwChangeActivity)
        var validate = false // 비밀번호 확인 결과

        pwChange_confirm.setOnClickListener {
            val password = pwChange_exist.text.toString()

            // 사용자의 현 비밀번호 확인
            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)

                    val success = jsonObject.getBoolean("success")
                    if (success == true) { // 현재 비밀번호가 맞을 경우
                        Toast.makeText(applicationContext, "확인 완료!", Toast.LENGTH_LONG).show()
                        validate = true // 비밀번호 확인 결과 true
                    } else { // 현재 비밀번호가 아닌 경우
                        Toast.makeText(applicationContext, "일치하지 않습니다!", Toast.LENGTH_LONG).show()
                        validate = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.

            val loginRequest = LoginRequest(userID, password, responseListener)
            val queue = Volley.newRequestQueue(this@PwChangeActivity)
            queue.add(loginRequest)
        }

        pwChange_btn.setOnClickListener {

            if (pwChange_pw1.text.toString() == "" || pwChange_pw2.text.toString() == "") {

                builder.setMessage("비밀번호를 입력해주세요.").setPositiveButton("확인", null).create()
                builder.show()
            } else {
                if (validate == true) {
                    if (pwChange_pw1.text.toString().equals(pwChange_pw2.text.toString())) {
                        var pw = pwChange_pw1.text.toString()
                        if (pw.equals(my_pw)) {
                            builder.setMessage("현재 비밀번호와 일치합니다.").setPositiveButton("확인", null).create()
                            builder.show()
                        } else {
                            val changeListener = Response.Listener<String> { response ->
                                try {
                                    val jsonObject = JSONObject(response)
                                    val success = jsonObject.getBoolean("success")
                                    if (success == true) { // 비밀번호 변경을 성공했을 때
                                        Toast.makeText(
                                            applicationContext,
                                            "변경 완료!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val editor : SharedPreferences.Editor = pref.edit()
                                        editor.remove("pw") // 기존 패스워드를 지움
                                        editor.putString("pw",pw) // 새로운 패스워드 등록

                                        editor.commit()
                                        finish()
                                    } else { // 비밀번호 변경 실패했을 때
                                        Toast.makeText(
                                            applicationContext,
                                            "변경 실패!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        return@Listener
                                    }

                                } catch (e: JSONException) {
                                    e.printStackTrace()

                                }
                            }
                            val changeRequest = PwChangeRequest(userID, pw, changeListener)
                            val queue = Volley.newRequestQueue(this@PwChangeActivity)
                            queue.add(changeRequest)
                        }
                    } else { // 비밀번호 일치 X
                        Toast.makeText(
                            applicationContext,
                            "입력한 비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else { // 비밀번호 확인 X
                    Toast.makeText(applicationContext, "현 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show()
                }
            }


        }
    }
}
