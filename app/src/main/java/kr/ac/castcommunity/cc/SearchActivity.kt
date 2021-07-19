package kr.ac.castcommunity.cc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.search.*
import kr.ac.castcommunity.cc.Board.BoardDecoration
import kr.ac.castcommunity.cc.adapters.BoardAdapter
import kr.ac.castcommunity.cc.adapters.SearchAdapter
import kr.ac.castcommunity.cc.models.Board
import kr.ac.castcommunity.cc.request.SearchListRequest
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    private var mSearchRecyclerView: RecyclerView? = null // 검색 RecyclerView 지정
    val mDatas: ArrayList<Board> = ArrayList() // 데이터를 담을 ArrayList
    private var mAdapter: SearchAdapter? = null // Adapter 변수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        mSearchRecyclerView = search_recyclerView
        mSearchRecyclerView!!.addItemDecoration(BoardDecoration(20)) // 아이템간 구분자 지정
        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean { // 텍스트가 바뀔 때
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean { // 검색 버튼을 눌렀을 때
                // query : 검색어
                mDatas.clear()
                val responseListener = Response.Listener<String> { response ->
                    try {
                        val search_array = JSONArray(response)
                        Log.d("response", "search response Start")
                        for (i in 0 until search_array.length()) {
                            val jobject = search_array.getJSONObject(i)
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

                            if (anonymous == 1) {
                                writer = "익명"
                            }
                            if (success == true) {
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
                        mAdapter = SearchAdapter(this@SearchActivity, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                        mSearchRecyclerView!!.adapter = mAdapter

                        // mSearchRecyclerView!!.addItemDecoration(BoardDecoration(20)) // 아이템간 구분자 지정
                        val lm = LinearLayoutManager(this@SearchActivity)
                        lm.reverseLayout = true // 출력 역순으로
                        lm.stackFromEnd = true // xml에서도 지정이 가능함
                        mSearchRecyclerView!!.layoutManager = lm
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val searchRequest = SearchListRequest(query, responseListener)
                val queue = Volley.newRequestQueue(this@SearchActivity)
                queue.add(searchRequest)
                return true
            }
        })


        search_back.setOnClickListener {
            this.finish()
        }
    }
}