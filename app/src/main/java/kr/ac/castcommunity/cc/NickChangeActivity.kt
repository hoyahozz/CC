package kr.ac.castcommunity.cc

import NickChangeRequest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.nickchange.*
import kotlinx.android.synthetic.main.pwchange.*
import kr.ac.castcommunity.cc.request.PwChangeRequest
import org.json.JSONException
import org.json.JSONObject

class NickChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nickchange)

        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE) // 초기화
        val userID = pref.getString("id", "").toString() // 저장한 값 불러오는 과정
        val my_pw = pref.getString("pw", "").toString()
        val my_nick = pref.getString("nickname", "").toString()
        var confirm: String = ""
        var validate = false // 닉네임 확인 결과
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@NickChangeActivity)

        nickChange_exist.setText(my_nick) // 기존 닉네임 텍스트로 저장

        nickChange_confirm.setOnClickListener {

            confirm = "0" // PHP에서 0일 때 SELECT 문 실행
            // 닉네임 중복확인
            val nickname = nickChange_exist.text.toString()
            if (nickname == "") {
                builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create()
                builder.show()
            } else {
                val confirmListener = Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success == true) { // 중복 닉네임이 존재할 때
                            builder.setMessage("다른 닉네임을 입력해주세요.").setPositiveButton("확인", null)
                                .create()
                            builder.show()
                            validate = false
                        } else { // 중복 닉네임 존재 X
                            Toast.makeText(applicationContext, "사용 가능한 아이디입니다!", Toast.LENGTH_LONG)
                                .show()
                            validate = true
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val nickRequest = NickChangeRequest(userID, nickname, confirm, confirmListener)
                val queue = Volley.newRequestQueue(this@NickChangeActivity)
                queue.add(nickRequest)
            }
        }

        nickChange_btn.setOnClickListener {
            confirm = "1" // PHP에서 1일 때 UPDATE 문 실행
            if (nickChange_pw.text.toString() == "" || nickChange_exist.text.toString() == "") {
                builder.setMessage("정보를 모두 입력해주세요.").setPositiveButton("확인", null).create()
                builder.show()
            } else {
                if(validate == true) { // 비밀번호가 현 비밀번호와 일치할 때만 실행
                    if (nickChange_pw.text.toString() == my_pw) { // 중복확인을 완료했을 때만 실행
                        val nickname = nickChange_exist.text.toString()
                        val changeListener = Response.Listener<String> { response ->
                            try {
                                val jsonObject = JSONObject(response)
                                val success = jsonObject.getBoolean("success")
                                if (success == true) { // 닉네임 변경을 성공했을 때
                                    Toast.makeText(
                                        applicationContext,
                                        "변경 완료!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val editor: SharedPreferences.Editor = pref.edit()
                                    editor.remove("nickname") // 기존 닉네임을 지움
                                    editor.putString("nickname", nickname) // 새로운 닉네임 등록
                                    editor.commit()
                                    finish()
                                } else { // 닉네임 변경 실패했을 때
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
                        val changeRequest =
                            NickChangeRequest(userID, nickname, confirm, changeListener)
                        val queue = Volley.newRequestQueue(this@NickChangeActivity)
                        queue.add(changeRequest)
                    } else {
                        builder.setMessage("비밀번호를 확인해주세요.").setPositiveButton("확인", null).create()
                        builder.show()
                    }
                }
                else {
                    // 중복확인 X
                    builder.setMessage("닉네임 중복확인을 실행해주세요.").setPositiveButton("확인", null).create()
                    builder.show()

                }
            }

        }

    }
}
