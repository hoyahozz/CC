package kr.ac.castcommunity.cc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kr.ac.castcommunity.cc.Board.BoardDecoration
import kr.ac.castcommunity.cc.Toolbar.BoardToolbarActivity
import kr.ac.castcommunity.cc.adapters.BoardAdapter
import kr.ac.castcommunity.cc.request.BoardListRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import kr.ac.castcommunity.cc.models.Board
import android.os.Build.VERSION_CODES.O
import kr.ac.castcommunity.cc.BoardActivity
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.board.*


class BoardActivity : BoardToolbarActivity() {

    private var mPostRecyclerView: RecyclerView? = null

    private var mAdpater: BoardAdapter? = null
    val mDatas : ArrayList<Board> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)
        Log.d("oncreate","oncreate Start")

        mPostRecyclerView = recyclerView

        val responseListener = Response.Listener<JSONArray> { response ->
            try {
                Log.d("response","response Start")
                // val jsonarray = response.getJSONArray("result")
                for (i in 0 until response.length()) {
                    val jobject = response.getJSONObject(i)
                    val success = jobject.getBoolean("success")
                    val bnum = jobject.getInt("bnum")
                    val title = jobject.getString("title")
                    val content = jobject.getString("content")
                    val date = jobject.getString("date")
                    val writer = jobject.getString("writer")

                    if (success == true) {
                        mDatas.add(Board(bnum, title, content, date, writer))
                    }
                    else { // 로그인에 실패한 경우
                        return@Listener
                    }
                }
                mAdpater = BoardAdapter(this, mDatas)
                mPostRecyclerView!!.adapter = mAdpater

                mPostRecyclerView!!.addItemDecoration(BoardDecoration(20))
                val lm = LinearLayoutManager(this)
                mPostRecyclerView!!.layoutManager = lm
                mPostRecyclerView!!.setHasFixedSize(true)
                val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
                mPostRecyclerView!!.addItemDecoration(decoration)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley를 이용해서 요청함.

        val loginRequest = BoardListRequest(responseListener)
        val queue = Volley.newRequestQueue(this@BoardActivity)
        queue.add(loginRequest)
}

}
