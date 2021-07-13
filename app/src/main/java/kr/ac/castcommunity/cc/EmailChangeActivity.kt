package kr.ac.castcommunity.cc

import EmailChangeRequest
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.emailchange.*
import kotlinx.android.synthetic.main.nickchange.*
import org.json.JSONException
import org.json.JSONObject

class EmailChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emailchange)
        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE) // 초기화
        val userID = pref.getString("id", "").toString() // 저장한 값 불러오는 과정
        val my_pw = pref.getString("pw", "").toString()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        emailChange_btn.setOnClickListener {

            if (emailChange_exist.text.toString() == "" || emailChange_exist.text.toString() == "") {
                builder.setMessage("정보를 모두 입력해주세요.").setPositiveButton("확인", null).create()
                builder.show()
            } else {
                if (emailChange_pw.text.toString() == my_pw) { // 중복확인을 완료했을 때만 실행
                    val email = emailChange_exist.text.toString()
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
                                editor.remove("email") // 기존 닉네임을 지움
                                editor.putString("email", email) // 새로운 닉네임 등록
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
                        EmailChangeRequest(userID, email, changeListener)
                    val queue = Volley.newRequestQueue(this@EmailChangeActivity)
                    queue.add(changeRequest)
                } else {
                    builder.setMessage("비밀번호를 확인해주세요.").setPositiveButton("확인", null).create()
                    builder.show()
                }
            }
        }
    }
}
