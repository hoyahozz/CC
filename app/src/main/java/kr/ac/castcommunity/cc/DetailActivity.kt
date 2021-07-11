package kr.ac.castcommunity.cc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.write.*
import kr.ac.castcommunity.cc.Board.BoardDecoration
import kr.ac.castcommunity.cc.Toolbar.DetailToolbarActivity
import kr.ac.castcommunity.cc.adapters.BoardAdapter
import kr.ac.castcommunity.cc.adapters.CommentAdapter
import kr.ac.castcommunity.cc.models.Board
import kr.ac.castcommunity.cc.models.Comment
import kr.ac.castcommunity.cc.request.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import android.os.Build.VERSION_CODES.O
import kr.ac.castcommunity.cc.DetailActivity
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService



class DetailActivity : DetailToolbarActivity() {

    private var mCommentRecyclerView: RecyclerView? = null
    private var mAdpater: CommentAdapter? = null
    val mDatas: ArrayList<Comment> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)

        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE)
        val writer = pref.getString("nickname", "").toString()
        val boardid = intent.getIntExtra("bnum", 0)

        val DetailresponseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                val title = jsonObject.getString("title")
                val content = jsonObject.getString("content")
                val writer = jsonObject.getString("writer")
                val date = jsonObject.getString("date")
                if (success == true) {
                    Toast.makeText(applicationContext, "게시판 불러오기 성공!", Toast.LENGTH_LONG).show()
                    detail_content.text = content
                    detail_title.text = title
                    detail_writer.text = writer
                    detail_date.text = date
                } else { // 로그인에 실패한 경우
                    Toast.makeText(applicationContext, "게시판 불러오기 실패!", Toast.LENGTH_LONG).show()
                    return@Listener
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley를 이용해서 요청함.

        val detailRequest = DetailRequest(boardid, DetailresponseListener)
        val queue = Volley.newRequestQueue(this@DetailActivity)
        queue.add(detailRequest)

        // 게시물 번호를 담기위한 배열
        mCommentRecyclerView = comment_recyclerView

        val responseListener = Response.Listener<String> { response ->
            try {
                val comment_array = JSONArray(response)
                Log.d("response", "comment response Start")
                for (i in 0 until comment_array.length()) {
                    val jobject = comment_array.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val boardid = jobject.getInt("boardid")
                    val commentid = jobject.getInt("commentid")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")
                    val writer = jobject.getString("writer")

                    if (success == true) {
                        mDatas.add(Comment(boardid, commentid, content, date, writer))
                    } else {
                        return@Listener
                    }
                }
                mAdpater = CommentAdapter(this, mDatas)
                mCommentRecyclerView!!.adapter = mAdpater
                mCommentRecyclerView!!.addItemDecoration(BoardDecoration(20))
                val lm = LinearLayoutManager(this)
                lm.reverseLayout = true // 출력 역순으로
                lm.stackFromEnd = true
                mCommentRecyclerView!!.layoutManager = lm
                mCommentRecyclerView!!.setHasFixedSize(true)
                val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
                mCommentRecyclerView!!.addItemDecoration(decoration)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley를 이용해서 요청함.

        val commentRequest = CommentRequest(boardid, responseListener)
        // 생성 Request 를 queue 에 추가
        queue.add(commentRequest)

        // 리사이클러뷰에서는 setOnItemClickListener 존재 X )

        val write_btn = comment_write_btn

        write_btn.setOnClickListener() {
            val content = comment_content.text.toString()
            val commentWriteListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) {// 글 등록에 성공한 경우
                        Toast.makeText(applicationContext, "글쓰기 성공!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@DetailActivity, DetailActivity::class.java)
                        intent.putExtra("bnum", boardid)
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
            val writeRequest = CommentWriteRequest(boardid, writer, content, commentWriteListener)
            queue.add(writeRequest)
        }


        comment_swipe.setOnRefreshListener {
            // 새로고침했을 때 반응
            mDatas.clear()
            val responseListener = Response.Listener<String> { response ->
                try {
                    val comment_array = JSONArray(response)
                    Log.d("response", "comment response Start")
                    for (i in 0 until comment_array.length()) {
                        val jobject = comment_array.getJSONObject(i)
                        val success = jobject.getBoolean("success")
                        val boardid = jobject.getInt("boardid")
                        val commentid = jobject.getInt("commentid")
                        val content = jobject.getString("content")
                        val date = jobject.getString("date")
                        val writer = jobject.getString("writer")

                        if (success == true) {
                            mDatas.add(Comment(boardid, commentid, content, date, writer))
                        } else {
                            return@Listener
                        }
                    }
                    mAdpater = CommentAdapter(this, mDatas)
                    mCommentRecyclerView!!.adapter = mAdpater
                    val lm = LinearLayoutManager(this)
                    lm.reverseLayout = true // 출력 역순으로
                    lm.stackFromEnd = true
                    mCommentRecyclerView!!.layoutManager = lm
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.

            val commentRequest = CommentRequest(boardid, responseListener)
            // 생성 Request 를 queue 에 추가
            queue.add(commentRequest)
            comment_swipe.isRefreshing = false
        }
    }
}
