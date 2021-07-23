package kr.ac.castcommunity.cc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.message_detail.*
import kotlinx.android.synthetic.main.message_detail_toolbar.*
import kr.ac.castcommunity.cc.Adapters.MessageDetailAdapter
import kr.ac.castcommunity.cc.Decoration.BoardDecoration
import kr.ac.castcommunity.cc.Models.Message
import kr.ac.castcommunity.cc.Request.BoardDeleteRequest
import kr.ac.castcommunity.cc.Request.MessageDeleteRequest
import kr.ac.castcommunity.cc.Request.MessageDetailRequest
import kr.ac.castcommunity.cc.Toolbar.MessageDetailToolbarActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MessageDetailActivity : MessageDetailToolbarActivity() {

    private var mMessageDetailRecyclerView: RecyclerView? = null
    val mDatas: java.util.ArrayList<Message> = java.util.ArrayList() // 데이터를 담을 ArrayList
    private var mAdapter: MessageDetailAdapter? = null // Adapter 변수


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_detail)

        val pref: SharedPreferences? = this.getSharedPreferences(
            "mine",
            Context.MODE_PRIVATE
        ) // SharedPreferences 초기화


        val messageroom = intent.getIntExtra("messageroom",0)
        val my_nick = pref?.getString("nickname", "").toString()

        mMessageDetailRecyclerView = message_detail_recyclerView
        mDatas.clear()

        mMessageDetailRecyclerView!!.addItemDecoration(BoardDecoration(1))
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonarray = JSONArray(response)
                for (i in 0 until jsonarray.length()) { // 받아온 데이터의 길이만큼 계속 받아옴
                    val jobject = jsonarray.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val messageroom = jobject.getInt("messageroom")
                    val messagenum = jobject.getInt("messagenum")
                    val receinick = jobject.getString("receinick")
                    val sendnick = jobject.getString("sendnick")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")

                    if(receinick == my_nick) {
                        message_detail_toolbar_nickname.text = sendnick
                    } else {
                        message_detail_toolbar_nickname.text = receinick
                    }

                    if (success == true) { // 게시물을 받아오는데 성공했을 때
                        mDatas.add(
                            Message(
                                messageroom,
                                messagenum,
                                sendnick,
                                receinick,
                                content,
                                date
                            )
                        )
                    } else {
                        return@Listener
                    }
                }


                mAdapter = MessageDetailAdapter(this@MessageDetailActivity, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                mMessageDetailRecyclerView?.adapter = mAdapter
                val lm = LinearLayoutManager(this@MessageDetailActivity)
                lm.reverseLayout = true // 출력 역순으로
                lm.stackFromEnd = true // xml에서도 지정이 가능함
                mMessageDetailRecyclerView?.layoutManager = lm
                mMessageDetailRecyclerView?.setHasFixedSize(true)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley 를 이용해서 요청함.

        val messageDetailRequest = MessageDetailRequest(messageroom.toString(), my_nick, responseListener)
        val queue = Volley.newRequestQueue(this)
        queue.add(messageDetailRequest)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val messageroom = intent.getIntExtra("messageroom",0)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        when (item.itemId) {

            R.id.action_send -> {
                //보내기 버튼 눌렀을 때
                val intent = Intent(applicationContext, MessageSendActivity ::class.java)
                intent.putExtra("other_nick",message_detail_toolbar_nickname.text)
                intent.putExtra("messageroom",messageroom)
                startActivity(intent)
                return true
            }
            R.id.action_message_delete -> {
                //메뉴 버튼 눌렀을 때
                builder.setMessage("정말로 삭제하시겠습니까?")
                builder.setPositiveButton("확인") { DialogInterface, i ->
                    val deleteListener = Response.Listener<String> { response ->
                        try {
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getBoolean("success")
                            if (success == true) { // 글 삭제에 성공했을 때
                                Toast.makeText(applicationContext, "삭제 완료!", Toast.LENGTH_LONG)
                                    .show()
                                finish()
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("number",3)
                                startActivity(intent)
                            } else { // 글 삭제에 실패했을 때
                                Toast.makeText(applicationContext, "삭제 실패!", Toast.LENGTH_LONG)
                                    .show()
                                return@Listener
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    // 서버로 Volley를 이용해서 요청함.
                    val deleteRequest = MessageDeleteRequest(messageroom.toString(), deleteListener)
                    val queue = Volley.newRequestQueue(this)
                    queue.add(deleteRequest)
                }
                builder.setNegativeButton("취소") { DialogInterface, i ->

                }
                builder.show()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}