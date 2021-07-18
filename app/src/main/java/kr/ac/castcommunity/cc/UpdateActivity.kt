package kr.ac.castcommunity.cc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.update.*
import kotlinx.android.synthetic.main.update_toolbar.*
import kr.ac.castcommunity.cc.Toolbar.UpdateToolbarActivity
import kr.ac.castcommunity.cc.request.BoardDeleteRequest
import kr.ac.castcommunity.cc.request.BoardUpdateRequest
import kr.ac.castcommunity.cc.request.DetailRequest
import org.json.JSONException
import org.json.JSONObject

class UpdateActivity : UpdateToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update)
        val boardid = intent.getIntExtra("boardid", 0)

        val DetailresponseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                val title = jsonObject.getString("title")
                val content = jsonObject.getString("content")
                if (success == true) {
                    update_content.setText(content)
                    update_title.setText(title)
                } else {
                    Toast.makeText(applicationContext, "게시판 불러오기 실패!", Toast.LENGTH_LONG).show()
                    return@Listener
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley를 이용해서 요청함.

        val detailRequest = DetailRequest(boardid, DetailresponseListener)
        val queue = Volley.newRequestQueue(this)
        queue.add(detailRequest)

        var anonymous = 0
        update_btn.setOnClickListener {
            val title = update_title.text.toString()
            val content = update_content.text.toString()
            if (update_anonymous.isChecked) {
                anonymous = 1
            }
            val updateListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) { // 글 수정에 성공했을 때
                        Toast.makeText(applicationContext, "수정 완료!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("bnum", boardid)
                        startActivity(intent)
                    } else { // 글 수정에 실패했을 때
                        Toast.makeText(applicationContext, "수정 실패!", Toast.LENGTH_LONG).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            // 서버로 Volley를 이용해서 요청함.
            val updateRequest = BoardUpdateRequest(
                title,
                content,
                boardid.toString(),
                anonymous.toString(),
                updateListener
            )
            val queue = Volley.newRequestQueue(this)
            queue.add(updateRequest)

        }
    }
}
