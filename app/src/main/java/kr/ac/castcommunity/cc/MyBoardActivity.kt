package kr.ac.castcommunity.cc

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.my_board.*
import kotlinx.android.synthetic.main.my_board_toolbar.*
import kr.ac.castcommunity.cc.Decoration.BoardDecoration
import kr.ac.castcommunity.cc.Toolbar.MyBoardToolbarActivity
import kr.ac.castcommunity.cc.Adapters.MyBoardAdapter
import kr.ac.castcommunity.cc.Models.Board
import kr.ac.castcommunity.cc.Request.MyBoardListRequest
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class MyBoardActivity : MyBoardToolbarActivity() {

    private var mBoardRecyclerView: RecyclerView? = null

    private var mAdapter: MyBoardAdapter? = null // Adapter 변수
    val mDatas: ArrayList<Board> = ArrayList() // 데이터를 담을 ArrayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_board)
        // MariaDB - PHP - Android 연동
        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE)
        val my_id = pref.getString("id", "").toString()
        var type = intent.getStringExtra("type").toString()

        if(type == "board") {
            my_board_name.text = "내가 쓴 글"
        } else {
            my_board_name.text = "댓글 단 글"
        }
        mBoardRecyclerView = my_board_recyclerView

        my_board_list(my_id, type)
        my_board_swipe(my_id, type)

    }


    fun my_board_list(my_id : String, type : String) {
        val responseListener = Response.Listener<String> { response ->
            try {
                // Log.d("response", "response Start")
                val jsonarray = JSONArray(response)
                for (i in 0 until jsonarray.length()) { // 받아온 데이터의 길이만큼 계속 받아옴
                    val jobject = jsonarray.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val bnum = jobject.getInt("bnum")
                    val btype = jobject.getString("btype")
                    var writer = jobject.getString("writer")
                    val title = jobject.getString("title")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")
                    val memId = jobject.getString("memId")
                    val cnt = jobject.getInt("cnt")
                    val anonymous = jobject.getInt("anonymous")

                    if (anonymous == 1) { // 익명일 때 닉네임 익명으로 설정
                        writer = "익명"
                    }

                    if (success == true) { // 게시물을 받아오는데 성공했을 때
                        mDatas.add(
                            Board(
                                bnum,
                                btype,
                                writer,
                                title,
                                content,
                                memId,
                                date,
                                cnt,
                                anonymous
                            )
                        ) // ArrayList 에 데이터 추가
                    } else {
                        return@Listener
                    }
                }
                mAdapter = MyBoardAdapter(this, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                mBoardRecyclerView!!.adapter = mAdapter

                mBoardRecyclerView!!.addItemDecoration(BoardDecoration(1)) // 아이템간 구분자 지정
                val lm = LinearLayoutManager(this)
                lm.reverseLayout = true // 출력 역순으로
                lm.stackFromEnd = true // xml에서도 지정이 가능함
                mBoardRecyclerView!!.layoutManager = lm
                mBoardRecyclerView!!.setHasFixedSize(true)
                // 아이템 항목을 추가할 때마다 RecyclerView의 크기 변경이 일정하다는 것을 확인
                // 고정된 Size를 가지는 RecyclerView일 경우 setHasFixedSize를 사용하면서 불필요한 리소스를 아낄 수 있다.
                val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
                mBoardRecyclerView!!.addItemDecoration(decoration) // 아이템 수직 정렬하게 지정

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley 를 이용해서 요청함.
        val BoardRequest = MyBoardListRequest(my_id, type, responseListener)
        val queue = Volley.newRequestQueue(this@MyBoardActivity)
        queue.add(BoardRequest)
    }

    // 리사이클러뷰에서는 setOnItemClickListener 존재 X )

    fun my_board_swipe(my_id: String, type : String) {
        my_board_swipe.setOnRefreshListener { // 새로고침했을 때 반응
            mDatas.clear() // 데이터를 다시 지워줌
            val responseListener = Response.Listener<String> { response ->
                try { // 데이터를 확인하고 다시 넣는 과정
                    val jsonarray = JSONArray(response)
                    for (i in 0 until jsonarray.length()) { // 받아온 데이터의 길이만큼 계속 받아옴
                        val jobject = jsonarray.getJSONObject(i)
                        val success = jobject.getBoolean("success")
                        val bnum = jobject.getInt("bnum")
                        val btype = jobject.getString("btype")
                        val title = jobject.getString("title")
                        val content = jobject.getString("content")
                        val date = jobject.getString("date")
                        var writer = jobject.getString("writer")
                        val memId = jobject.getString("memId")
                        val cnt = jobject.getInt("cnt")
                        val anonymous = jobject.getInt("anonymous")

                        if (anonymous == 1) { // 익명일 때 닉네임 익명으로 설정
                            writer = "익명"
                        }

                        if (success == true) { // 게시물을 받아오는데 성공했을 때
                            mDatas.add(
                                Board(
                                    bnum,
                                    btype,
                                    writer,
                                    title,
                                    content,
                                    memId,
                                    date,
                                    cnt,
                                    anonymous
                                )
                            ) // ArrayList 에 데이터 추가
                        } else { // 로그인에 실패한 경우
                            return@Listener
                        }
                    }
                    mAdapter = MyBoardAdapter(this, mDatas)
                    mBoardRecyclerView!!.adapter = mAdapter
                    val lm = LinearLayoutManager(this)
                    lm.reverseLayout = true // 출력 역순으로
                    lm.stackFromEnd = true
                    mBoardRecyclerView!!.layoutManager = lm
                    mBoardRecyclerView!!.setHasFixedSize(true)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.
            val BoardRequest = MyBoardListRequest(my_id, type, responseListener)
            val queue = Volley.newRequestQueue(this@MyBoardActivity)
            queue.add(BoardRequest)
            my_board_swipe.isRefreshing = false
        }
    }
}