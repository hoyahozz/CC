package kr.ac.castcommunity.cc.Fragment

import ViewPagerAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_main.*
import kr.ac.castcommunity.cc.Board.MainBoardDecoration
import kr.ac.castcommunity.cc.BoardActivity
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.adapters.HotAdapter
import kr.ac.castcommunity.cc.models.Board
import kr.ac.castcommunity.cc.request.BoardTypeRequest
import kr.ac.castcommunity.cc.request.HotBoardListRequest
import org.json.JSONArray
import org.json.JSONException


class MainFragment : Fragment() {

    private var mHotRecyclerView: RecyclerView? = null
    val mDatas: java.util.ArrayList<Board> = java.util.ArrayList() // 데이터를 담을 ArrayList
    private var mAdapter: HotAdapter? = null // Adapter 변수

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_main, container, false)

        val pref: SharedPreferences? = this.activity?.getSharedPreferences(
            "board",
            Context.MODE_PRIVATE
        ) // SharedPreferences 초기화
        val editor: SharedPreferences.Editor =
            pref!!.edit() //  SharedPreferences 의 데이터를 저장/편집하기 위한 Editor 변수 선언
        editor.clear()

        boardList()


        mHotRecyclerView = view.findViewById<RecyclerView>(R.id.hot_recyclerView)
        mHotRecyclerView?.addItemDecoration(MainBoardDecoration(5)) // 아이템간 구분자 지정
        mDatas.clear()
        val responseListener = Response.Listener<String> { response ->
            try {
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
                        )
                    } else {
                        return@Listener
                    }
                }
                mAdapter = HotAdapter(activity, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                mHotRecyclerView?.adapter = mAdapter
                val lm = LinearLayoutManager(activity)
                mHotRecyclerView?.layoutManager = lm
                mHotRecyclerView?.setHasFixedSize(true)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley 를 이용해서 요청함.

        val BoardRequest = HotBoardListRequest(responseListener)
        val queue = Volley.newRequestQueue(this.activity)
        queue.add(BoardRequest)

        val main_viewPager = view.findViewById<ViewPager2>(R.id.main_viewPager)

        main_viewPager.adapter = ViewPagerAdapter(getList())


        val main_board1 = view.findViewById<Button>(R.id.main_board1)
        val main_board2 = view.findViewById<Button>(R.id.main_board2)
        val main_board3 = view.findViewById<Button>(R.id.main_board3)
        val main_board4 = view.findViewById<Button>(R.id.main_board4)
        val main_board5 = view.findViewById<Button>(R.id.main_board5)

        main_board1.setOnClickListener { view ->
            editor.putString("btype", main_board1.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board2.setOnClickListener { view ->
            editor.putString("btype", main_board2.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board3.setOnClickListener { view ->
            editor.putString("btype", main_board3.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board4.setOnClickListener { view ->
            editor.putString("btype", main_board4.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board5.setOnClickListener { view ->
            editor.putString("btype", main_board5.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        return view
    }


    private fun getList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.exam2, R.drawable.exam3, R.drawable.exam4)
    }

    fun boardList() {

        val BoardTypeListener = Response.Listener<String> { response ->
            try {
                val jsonarray = JSONArray(response)
                for (i in 0 until jsonarray.length()) { // 받아온 데이터의 길이만큼 계속 받아옴
                    val jsonObject = jsonarray.getJSONObject(i)
                    val success = jsonObject.getBoolean("success")
                    val title = jsonObject.getString("title")
                    val btype = jsonObject.getString("btype")
                    if (success == true) {
                        if (btype == "자유게시판") {
                            main_text1.text = title
                        } else if (btype == "정보게시판") {
                            main_text2.text = title
                        } else if (btype == "장터") {
                            main_text3.text = title
                        } else if (btype == "퇴사자게시판") {
                            main_text4.text = title
                        } else if (btype == "동아리") {
                            main_text5.text = title
                        }
                    } else {
                        return@Listener
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley를 이용해서 요청함.

        val boardTypeRequest = BoardTypeRequest(BoardTypeListener)
        val queue = Volley.newRequestQueue(this.activity)
        queue.add(boardTypeRequest)
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}