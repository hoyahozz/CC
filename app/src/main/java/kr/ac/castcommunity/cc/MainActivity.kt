package kr.ac.castcommunity.cc

import ViewPagerAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.main.*
import kr.ac.castcommunity.cc.Board.BoardDecoration
import kr.ac.castcommunity.cc.Toolbar.MainToolbar
import kr.ac.castcommunity.cc.adapters.HotAdapter
import kr.ac.castcommunity.cc.models.Board
import kr.ac.castcommunity.cc.request.BoardListRequest
import kr.ac.castcommunity.cc.request.BoardTypeRequest
import kr.ac.castcommunity.cc.request.HotBoardListRequest
import org.json.JSONArray
import org.json.JSONException

class MainActivity : MainToolbar() {

    private var mHotRecyclerView: RecyclerView? = null
    val mDatas: java.util.ArrayList<Board> = java.util.ArrayList() // 데이터를 담을 ArrayList
    private var mAdapter: HotAdapter? = null // Adapter 변수
    private val fragmentThree by lazy { NoteActivity() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val pref: SharedPreferences =
            getSharedPreferences("board", MODE_PRIVATE) // SharedPreferences 초기화
        val editor: SharedPreferences.Editor =
            pref.edit() //  SharedPreferences 의 데이터를 저장/편집하기 위한 Editor 변수 선언
        editor.clear()

        boardList()
        hotBoardList()

        main_viewPager.adapter = ViewPagerAdapter(getList())

        var mainboard = main_board
        mainboard.setOnClickListener {
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }
        useToolbar()

        main_board1.setOnClickListener {
            editor.putString("btype", main_board1.text.toString())
            editor.commit()
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board2.setOnClickListener {
            editor.putString("btype", main_board2.text.toString())
            editor.commit()
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board3.setOnClickListener {
            editor.putString("btype", main_board3.text.toString())
            editor.commit()
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board4.setOnClickListener {
            editor.putString("btype", main_board4.text.toString())
            editor.commit()
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }

        main_board5.setOnClickListener {
            editor.putString("btype", main_board5.text.toString())
            editor.commit()
            val intent = Intent(applicationContext, BoardActivity::class.java)
            startActivity(intent)
        }

        main_bnv.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.third -> {
                        //changeFragment(fragmentThree)
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_container, fragmentThree)
                            .commit()
                    }
                }
                true
            }
        }


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
        val queue = Volley.newRequestQueue(this@MainActivity)
        queue.add(boardTypeRequest)
    }
    
    fun hotBoardList() {
        mHotRecyclerView = hot_recyclerView
        mHotRecyclerView!!.addItemDecoration(BoardDecoration(5)) // 아이템간 구분자 지정
        
        // MariaDB - PHP - Android 연동
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
                mAdapter = HotAdapter(this@MainActivity, mDatas) // 게시물 어댑터 연결, 데이터를 보냄
                mHotRecyclerView!!.adapter = mAdapter

                val lm = LinearLayoutManager(this)
                lm.reverseLayout = true // 출력 역순으로
                lm.stackFromEnd = true // xml에서도 지정이 가능함
                mHotRecyclerView!!.layoutManager = lm
                mHotRecyclerView!!.setHasFixedSize(true)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        //서버로 Volley 를 이용해서 요청함.

        val BoardRequest = HotBoardListRequest(responseListener)
        val queue = Volley.newRequestQueue(this@MainActivity)
        queue.add(BoardRequest)
    }

//    private fun changeFragment(fragment: Fragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.main_container, fragment)
//            .commit()
//    }


}
