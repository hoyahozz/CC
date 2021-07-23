package kr.ac.castcommunity.cc

import RecommendAddRequest
import RecommendDeleteRequest
import RecommendRequest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.detail_toolbar.*
import kr.ac.castcommunity.cc.Decoration.MainBoardDecoration
import kr.ac.castcommunity.cc.Toolbar.DetailToolbarActivity
import kr.ac.castcommunity.cc.Adapters.CommentAdapter
import kr.ac.castcommunity.cc.Models.Comment
import kr.ac.castcommunity.cc.Request.BoardDeleteRequest
import kr.ac.castcommunity.cc.Request.CommentRequest
import kr.ac.castcommunity.cc.Request.CommentWriteRequest
import kr.ac.castcommunity.cc.Request.DetailRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class DetailActivity : DetailToolbarActivity() {

    private var mCommentRecyclerView: RecyclerView? = null // 댓글 RecyclerView 지정
    private var mAdpater: CommentAdapter? = null
    val mDatas: ArrayList<Comment> = ArrayList()

    var other_nick : String = "hello"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)
        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE) // 초기화
        val nickname = pref.getString("nickname", "").toString() // 저장한 값 불러오는 과정
        val my_id = pref.getString("id", "").toString()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        var boardid = intent.getIntExtra("bnum", 0)
        // 저장 값이 없으면 ""(공백)으로 불러옴


        val DetailresponseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                val title = jsonObject.getString("title")
                val content = jsonObject.getString("content")
                val writer = jsonObject.getString("writer")
                val date = jsonObject.getString("date")
                val memId = jsonObject.getString("memId")
                val cnt = jsonObject.getInt("cnt")
                val anonymous = jsonObject.getInt("anonymous")
                val btype = jsonObject.getString("btype")

                if (success == true) {
                    detail_content.text = content
                    detail_title.text = title
                    other_nick = writer

                    if (anonymous == 1) {
                        detail_writer.text = "익명"
                    } else {
                        detail_writer.text = writer
                    }
                    detail_date.text = date
                    detail_cnt.text = cnt.toString()
                    detail_board_name.text = btype
                    if (my_id != memId) {
                        detail_recommend.isVisible = true
                    }
                } else {
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

        // 댓글 RecyclerView
        mCommentRecyclerView = comment_recyclerView

        // ★ PHP에서 JSONArray 를 리턴한다고 해서 JSONArrayRequest 를 해야하는 것이 아님.
        // 그리고 JSONArrayRequest 에서 getParams 함수는 먹히지 않음.
        val responseListener = Response.Listener<String> { response ->
            try {
                val comment_array = JSONArray(response)
                Log.d("response", "comment response Start")
                for (i in 0 until comment_array.length()) {
                    val jobject = comment_array.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val boardid = jobject.getInt("boardid")
                    val commentid = jobject.getInt("commentid")
                    val memId = jobject.getString("memId")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")
                    var writer = jobject.getString("writer")
                    val anonymous = jobject.getInt("anonymous")

                    if (anonymous == 1) {
                        writer = "익명"
                    }

                    if (success == true) {
                        mDatas.add(
                            Comment(
                                boardid,
                                commentid,
                                memId,
                                writer,
                                content,
                                date,
                                anonymous
                            )
                        )
                    } else {
                        return@Listener
                    }
                }
                mAdpater = CommentAdapter(this, mDatas, my_id)
                mCommentRecyclerView!!.adapter = mAdpater
                mCommentRecyclerView!!.addItemDecoration(MainBoardDecoration(20))
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
        val commentRequest = CommentRequest(boardid, responseListener)
        queue.add(commentRequest)


        val write_btn = comment_write_btn

        var com_anonymous = 0
        write_btn.setOnClickListener {
            if (comment_anonymous.isChecked) {
                com_anonymous = 1
            }
            val content = comment_content.text.toString()
            val commentWriteListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) {// 글 등록에 성공한 경우
                        finish()
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
            val writeRequest = CommentWriteRequest(
                my_id,
                boardid,
                nickname,
                content,
                com_anonymous.toString(),
                commentWriteListener
            )
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
                        val memId = jobject.getString("memId")
                        val content = jobject.getString("content")
                        val date = jobject.getString("date")
                        var writer = jobject.getString("writer")
                        val anonymous = jobject.getInt("anonymous")

                        if (anonymous == 1) { // 익명일 때 닉네임 익명으로 설정
                            writer = "익명"
                        }

                        if (success == true) {
                            mDatas.add(
                                Comment(
                                    boardid,
                                    commentid,
                                    memId,
                                    writer,
                                    content,
                                    date,
                                    anonymous
                                )
                            )
                        } else {
                            return@Listener
                        }
                    }
                    mAdpater = CommentAdapter(this, mDatas, my_id)
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

        detail_recommend.setOnClickListener {
            val recommendListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success == true) { // 해당 유저가 공감 버튼을 이미 눌렀을 때

                        builder.setMessage("공감 삭제하시겠습니까?")
                        builder.setPositiveButton("확인") { DialogInterface, i ->
                            val recommendDeleteListener = Response.Listener<String> { response ->
                                try {
                                    val jsonObject = JSONObject(response)
                                    val success = jsonObject.getBoolean("success")
                                    if (success == true) {
                                        Toast.makeText(
                                            applicationContext,
                                            "공감이 취소되었습니다!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        finish()
                                        val intent =
                                            Intent(this@DetailActivity, DetailActivity::class.java)
                                        intent.putExtra("bnum", boardid)
                                        startActivity(intent)
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            val recommendDeleteRequest = RecommendDeleteRequest(
                                boardid.toString(),
                                my_id,
                                recommendDeleteListener
                            )
                            queue.add(recommendDeleteRequest)
                        }
                        builder.setNegativeButton("취소") { DialogInterface, i ->

                        }
                        builder.show()


                    } else { // 공감 버튼을 누르지 않았을 때
                        builder.setMessage("공감하시겠습니까?")
                        builder.setPositiveButton("확인") { DialogInterface, i ->
                            val recommendAddListener = Response.Listener<String> { response ->
                                try {
                                    val jsonObject = JSONObject(response)
                                    val success = jsonObject.getBoolean("success")
                                    if (success == true) {
                                        Toast.makeText(
                                            applicationContext,
                                            "공감 성공!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        finish()
                                        val intent =
                                            Intent(this@DetailActivity, DetailActivity::class.java)
                                        intent.putExtra("bnum", boardid)
                                        startActivity(intent)
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            val recommendAddRequest =
                                RecommendAddRequest(boardid.toString(), my_id, recommendAddListener)
                            queue.add(recommendAddRequest)
                        }
                        builder.setNegativeButton("취소") { DialogInterface, i ->

                        }
                        builder.show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //서버로 Volley를 이용해서 요청함.
            val recommendRequest = RecommendRequest(boardid.toString(), my_id, recommendListener)
            queue.add(recommendRequest)

        }
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d("onPrepareOptionsMenu", "START")
        menuInflater.inflate(R.menu.board_menu, menu)
        val more = menu?.findItem(R.id.action_more)
        val send = menu?.findItem(R.id.action_board_send)
        val boardid = intent.getIntExtra("bnum", 0)
        var pref: SharedPreferences = getSharedPreferences("mine", Context.MODE_PRIVATE) // 초기화

        val my_id = pref.getString("id", "").toString()

        val DetailresponseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                val memId = jsonObject.getString("memId")
                if (success == true) {
                    if (my_id == memId) {
                        more?.setVisible(true)
                        send?.setVisible(false)
                    } else {
                        more?.setVisible(false)
                        send?.setVisible(true)
                    }
                } else { // 로그인에 실패한 경우
                    return@Listener
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val detailRequest = DetailRequest(boardid, DetailresponseListener)
        val queue = Volley.newRequestQueue(this@DetailActivity)
        queue.add(detailRequest)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val boardid = intent.getIntExtra("bnum", 0)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        when (item.itemId) {

            R.id.action_board_send -> {
                val intent = Intent(applicationContext, MessageSendActivity::class.java)
                intent.putExtra("other_nick", other_nick)
                startActivity(intent)
                return true
            }

            R.id.action_change -> {
                finish()
                val intent = Intent(applicationContext, UpdateActivity::class.java)
                intent.putExtra("boardid", boardid)
                startActivity(intent)
                return true
            }

            R.id.action_delete -> {
                builder.setMessage("정말로 삭제하시겠습니까?")
                builder.setPositiveButton("확인") { DialogInterface, i ->
                    val deleteListener = Response.Listener<String> { response ->
                        try {
                            Log.d("delete response :", boardid.toString())
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getBoolean("success")
                            if (success == true) { // 글 삭제에 성공했을 때
                                Toast.makeText(applicationContext, "삭제 완료!", Toast.LENGTH_LONG)
                                    .show()
                                finish()
                                val intent = Intent(this, BoardActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
                    val deleteRequest = BoardDeleteRequest(boardid.toString(), deleteListener)
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
