package kr.ac.castcommunity.cc

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.myinfo.*
import kotlinx.android.synthetic.main.write.*
import kr.ac.castcommunity.cc.Toolbar.MyInfoToolbarActivity
import kr.ac.castcommunity.cc.request.DeleteRequest
import kr.ac.castcommunity.cc.request.WriteRequest
import org.json.JSONException
import org.json.JSONObject

class MyInfoActivity : MyInfoToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myinfo)

        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE) // 초기화
        val userID = pref.getString("id", "").toString() // 저장한 값 불러오는 과정
        val userName = pref.getString("name", "").toString()
        val userNickname = pref.getString("nickname", "").toString()
        val builder = AlertDialog.Builder(this)


        my_userID.text = userID
        my_userName.text = userName
        my_userNickName.text = userNickname


        my_pwChange.setOnClickListener {
            val intent = Intent(this@MyInfoActivity, PwChangeActivity::class.java)
            startActivity(intent)
        }

        // 탈퇴 버튼을 눌렀을 때
        my_delete.setOnClickListener {
            builder.setMessage("정말로 탈퇴하시겠습니까?")
            builder.setPositiveButton("확인") { DialogInterface, i ->
                val editor: SharedPreferences.Editor = pref.edit()
                editor.clear(); // 세션 데이터 모두 초기화 후 초기화면으로 이동
                editor.commit();
                val deleteListener = Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success == true) {// 글 등록에 성공한 경우
                            Toast.makeText(applicationContext, "탈퇴되었습니다. 감사합니다.", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this@MyInfoActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else { // 글 등록에 실패한 경우
                            Toast.makeText(applicationContext, "탈퇴 실패!", Toast.LENGTH_LONG).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                //서버로 Volley를 이용해서 요청함.
                val deleteRequest = DeleteRequest(userID, deleteListener)
                val queue = Volley.newRequestQueue(this@MyInfoActivity)
                queue.add(deleteRequest)
            }
            builder.setNegativeButton("취소") { DialogInterface, i ->

            }
            builder.show()
        }


        // 로그아웃 버튼 눌렀을 때
        my_logout.setOnClickListener {
            builder.setMessage("로그아웃 하시겠습니까?")
            builder.setPositiveButton("확인") { DialogInterface, i ->
                val editor: SharedPreferences.Editor = pref.edit()
                editor.clear(); // 세션 데이터 모두 초기화 후 초기화면으로 이동
                editor.commit();
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("취소") { DialogInterface, i ->

            }
            builder.show()

        }

    }
}
