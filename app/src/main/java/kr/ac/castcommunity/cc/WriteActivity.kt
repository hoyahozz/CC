package kr.ac.castcommunity.cc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import kr.ac.castcommunity.cc.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.write.*
import kotlinx.android.synthetic.main.write_toolbar.*
import kr.ac.castcommunity.cc.BoardActivity
import kr.ac.castcommunity.cc.Toolbar.WriteToolbarActivity
import kr.ac.castcommunity.cc.request.WriteRequest
import org.json.JSONException
import org.json.JSONObject

class WriteActivity : WriteToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write)

        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE)
        var pref2: SharedPreferences = getSharedPreferences("board", Context.MODE_PRIVATE)
        val writer = pref.getString("nickname", "").toString()
        val my_id = pref.getString("id", "").toString()
        val this_btype = pref2.getString("btype","").toString()
        val write_btn = write_btn
        var anonymous = 0

        write_btn.setOnClickListener {
            if (write_anonymous.isChecked) {
                anonymous = 1
            }
            val title = write_title.text.toString()
            val content = write_content.text.toString()
            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) {// 글 등록에 성공한 경우
                        val intent = Intent(this@WriteActivity, BoardActivity::class.java)
                        startActivity(intent)
                    } else { // 글 등록에 실패한 경우
                        Toast.makeText(applicationContext, "글쓰기 실패!", Toast.LENGTH_LONG).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.
            val writeRequest =
                WriteRequest(my_id, title, content, writer, anonymous.toString(), this_btype, responseListener)
            val queue = Volley.newRequestQueue(this@WriteActivity)
            queue.add(writeRequest)
        }
    }
}

