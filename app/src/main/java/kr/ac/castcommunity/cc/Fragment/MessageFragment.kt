package kr.ac.castcommunity.cc.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kr.ac.castcommunity.cc.Adapters.HotAdapter
import kr.ac.castcommunity.cc.Adapters.MessageRoomAdapter
import kr.ac.castcommunity.cc.Decoration.BoardDecoration
import kr.ac.castcommunity.cc.Models.Board
import kr.ac.castcommunity.cc.Models.Message
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.Request.HotBoardListRequest
import kr.ac.castcommunity.cc.Request.MessageRoomRequest
import org.json.JSONArray
import org.json.JSONException


class MessageFragment : Fragment() {

    private var mMessageRoomRecyclerView: RecyclerView? = null
    val mDatas: java.util.ArrayList<Message> = java.util.ArrayList() // 데이터를 담을 ArrayList
    private var mAdapter: MessageRoomAdapter? = null // Adapter 변수

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {

        val view = inflater.inflate(R.layout.fragment_message, container, false)

        val pref: SharedPreferences? = this.activity?.getSharedPreferences(
            "mine",
            Context.MODE_PRIVATE
        ) // SharedPreferences 초기화

        val my_nick = pref?.getString("nickname", "").toString()

        mMessageRoomRecyclerView = view.findViewById<RecyclerView>(R.id.message_room_recyclerView)
        mDatas.clear()

        mMessageRoomRecyclerView!!.addItemDecoration(BoardDecoration(1))
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonarray = JSONArray(response)
                for (i in 0 until jsonarray.length()) { // 받아온 데이터의 길이만큼 계속 받아옴
                    val jobject = jsonarray.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val messageroom = jobject.getInt("messageroom")
                    val receinick = jobject.getString("receinick")
                    val sendnick = jobject.getString("sendnick")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")
                    if (success == true) { // 게시물을 받아오는데 성공했을 때
                        mDatas.add(
                            Message(
                                messageroom,
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
                mAdapter = MessageRoomAdapter(activity, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                mMessageRoomRecyclerView?.adapter = mAdapter
                val lm = LinearLayoutManager(activity)
                lm.reverseLayout = true // 출력 역순으로
                lm.stackFromEnd = true // xml에서도 지정이 가능함
                mMessageRoomRecyclerView?.layoutManager = lm
                mMessageRoomRecyclerView?.setHasFixedSize(true)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley 를 이용해서 요청함.

        val messageRoomRequest = MessageRoomRequest(my_nick, responseListener)
        val queue = Volley.newRequestQueue(this.activity)
        queue.add(messageRoomRequest)

        return view
    }
}
