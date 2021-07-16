package kr.ac.castcommunity.cc

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.castcommunity.cc.Board.BoardDecoration
import kr.ac.castcommunity.cc.Toolbar.BoardToolbarActivity
import kr.ac.castcommunity.cc.adapters.BoardAdapter
import kr.ac.castcommunity.cc.models.Board
<<<<<<< HEAD
import android.os.Build.VERSION_CODES.O
import kr.ac.castcommunity.cc.BoardActivity
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.item_board.*

=======
import java.util.ArrayList
>>>>>>> parent of 65318ca (merged)

class BoardActivity : BoardToolbarActivity() {

    private var mPostRecyclerView: RecyclerView? = null

    private var mAdpater: BoardAdapter? = null // Adapter 변수
    val mDatas: ArrayList<Board> = ArrayList() // 데이터를 담을 ArrayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)
<<<<<<< HEAD
        // Log.d("oncreate", "oncreate Start")


        // RecyclerView 선언
        mPostRecyclerView = recyclerView


        // MariaDB - PHP - Android 연동
        val responseListener = Response.Listener<JSONArray> { response ->
            try {
                // Log.d("response", "response Start")
                // val jsonarray = response.getJSONArray("result")
                for (i in 0 until response.length()) { // 받아온 데이터의 길이만큼 계속 받아옴
                    val jobject = response.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val bnum = jobject.getInt("bnum")
                    val btype = jobject.getString("btype")
                    val writer = jobject.getString("writer")
                    val title = jobject.getString("title")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")
                    val memId = jobject.getString("memId")
                    val cnt = jobject.getInt("cnt")
                    val anonymous = jobject.getInt("anonymous")

                    if (success == true) { // 게시물을 받아오는데 성공했을 때
                        mDatas.add(Board(bnum, btype, writer, title, content, memId, date, cnt, anonymous)) // ArrayList 에 데이터 추가
                    } else {
                        return@Listener
                    }
                }
                mAdpater = BoardAdapter(this, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                mPostRecyclerView!!.adapter = mAdpater

                mPostRecyclerView!!.addItemDecoration(BoardDecoration(20)) // 아이템간 구분자 지정
                val lm = LinearLayoutManager(this)
                lm.reverseLayout = true // 출력 역순으로
                lm.stackFromEnd = true // xml에서도 지정이 가능함
                mPostRecyclerView!!.layoutManager = lm
                mPostRecyclerView!!.setHasFixedSize(true)
                // 아이템 항목을 추가할 때마다 RecyclerView의 크기 변경이 일정하다는 것을 확인
                // 고정된 Size를 가지는 RecyclerView일 경우 setHasFixedSize를 사용하면서 불필요한 리소스를 아낄 수 있다.
                val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
                mPostRecyclerView!!.addItemDecoration(decoration) // 아이템 수직 정렬하게 지정

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley 를 이용해서 요청함.

        val BoardRequest = BoardListRequest(responseListener)
        val queue = Volley.newRequestQueue(this@BoardActivity)
        queue.add(BoardRequest)

        // 리사이클러뷰에서는 setOnItemClickListener 존재 X )

        board_swipe.setOnRefreshListener { // 새로고침했을 때 반응
            mDatas.clear() // 데이터를 다시 지워줌
            val responseListener = Response.Listener<JSONArray> { response ->
                try { // 데이터를 확인하고 다시 넣는 과정
                    Log.d("response", "response Start")
                    for (i in 0 until response.length()) {
                        val jobject = response.getJSONObject(i)
                        val success = jobject.getBoolean("success")
                        val bnum = jobject.getInt("bnum")
                        val btype = jobject.getString("btype")
                        val title = jobject.getString("title")
                        val content = jobject.getString("content")
                        val date = jobject.getString("date")
                        val writer = jobject.getString("writer")
                        val memId = jobject.getString("memId")
                        val cnt = jobject.getInt("cnt")
                        val anonymous = jobject.getInt("anonymous")

                        if (success == true) { // 게시물을 받아오는데 성공했을 때
                            mDatas.add(Board(bnum, btype, writer, title, content, memId, date, cnt, anonymous)) // ArrayList 에 데이터 추가
                        } else { // 로그인에 실패한 경우
                            return@Listener
                        }
                    }
                    mAdpater = BoardAdapter(this, mDatas)
                    mPostRecyclerView!!.adapter = mAdpater
                    val lm = LinearLayoutManager(this)
                    lm.reverseLayout = true // 출력 역순으로
                    lm.stackFromEnd = true
                    mPostRecyclerView!!.layoutManager = lm
                    mPostRecyclerView!!.setHasFixedSize(true)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.
            val BoardRequest = BoardListRequest(responseListener)
            val queue = Volley.newRequestQueue(this@BoardActivity)
            queue.add(BoardRequest)
            board_swipe.isRefreshing = false

        }

    }
=======

        mPostRecyclerView = findViewById(R.id.recyclerView)
        mDatas.add(Board("1", "title", "contents", "writer", "time"))
        mDatas.add(Board("2", "title", "contents", "writer", "time"))
        mDatas.add(Board("3", "title", "contents", "writer", "time"))
        mDatas.add(Board("4", "title", "contents", "writer", "time"))
        mDatas.add(Board("5", "title", "contents", "writer", "time"))
        // Adapter 연결
        mAdpater = BoardAdapter(this, mDatas)
        mPostRecyclerView!!.adapter = mAdpater

        mPostRecyclerView!!.addItemDecoration(BoardDecoration(20))


        val lm = LinearLayoutManager(this)
        mPostRecyclerView!!.layoutManager = lm
        mPostRecyclerView!!.setHasFixedSize(true)
>>>>>>> parent of 65318ca (merged)

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        mPostRecyclerView!!.addItemDecoration(decoration)
    }
}
