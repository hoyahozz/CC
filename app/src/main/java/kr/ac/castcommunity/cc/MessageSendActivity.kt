package kr.ac.castcommunity.cc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.message_send.*
import kotlinx.android.synthetic.main.message_send_toolbar.*
import kr.ac.castcommunity.cc.Request.MessageSendRequest
import kr.ac.castcommunity.cc.Toolbar.MessageDetailToolbarActivity
import kr.ac.castcommunity.cc.Toolbar.MessageSendToolbarActivity
import org.json.JSONException
import org.json.JSONObject

class MessageSendActivity : MessageSendToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_send)

        val pref: SharedPreferences? = this.getSharedPreferences(
            "mine",
            Context.MODE_PRIVATE
        ) // SharedPreferences 초기화
        val my_nick = pref?.getString("nickname", "").toString()
        val other_nick = intent.getStringExtra("other_nick").toString()
        val messageroom = intent.getIntExtra("messageroom", 0)
        message_send_button.setOnClickListener {
            val content = message_send_content.text.toString()
            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) {// 글 등록에 성공한 경우
                        finish()
                        val intent = Intent(this, MessageDetailActivity::class.java)
                        intent.putExtra("messageroom", messageroom)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else { // 글 등록에 실패한 경우
                        Toast.makeText(applicationContext, "쪽지 보내기 실패!", Toast.LENGTH_LONG).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.
            val sendRequest =
                MessageSendRequest(
                    my_nick,
                    other_nick,
                    content,
                    responseListener
                )
            val queue = Volley.newRequestQueue(this@MessageSendActivity)
            queue.add(sendRequest)

        }
    }
}

