package kr.ac.castcommunity.cc.Toolbar

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kr.ac.castcommunity.cc.*
import kr.ac.castcommunity.cc.request.DeleteRequest
import org.json.JSONException
import org.json.JSONObject

open class DetailToolbarActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_toolbar)
        // 액션바에 표시되는 제목의 표시 유무 설정(False -> 이름이 화면에 보이게 됨)
    }

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.detail_toolbar, null) as LinearLayout
        val activityContainer = fullView.findViewById<View>(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.detail_toolbar) //툴바 사용여부 결정(기본적으로 사용)
        if (useToolbar()) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()!!.setHomeAsUpIndicator(R.drawable.leftback)

        } else {
            toolbar.visibility = View.GONE
        }
    }

    protected fun useToolbar(): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.board_menu, menu)
        return true
    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//
//            R.id.action_change -> {
//                val intent = Intent(applicationContext, BoardActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//
//            R.id.action_delete -> {
//                builder.setMessage("정말로 삭제하시겠습니까?")
//                builder.setPositiveButton("확인") { DialogInterface, i ->
//                    val deleteListener = Response.Listener<String> { response ->
//                        try {
//                            val jsonObject = JSONObject(response)
//                            val success = jsonObject.getBoolean("success")
//                            if (success == true) { // 글 삭제에 성공했을 때
//                                Toast.makeText(applicationContext, "삭제 완료!", Toast.LENGTH_LONG)
//                                    .show()
//                                val intent = Intent(this, BoardActivity::class.java)
//                                startActivity(intent)
//                            } else { // 글 삭제에 실패했을 때
//                                Toast.makeText(applicationContext, "삭제 실패!", Toast.LENGTH_LONG).show()
//                                return@Listener
//                            }
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                    //서버로 Volley를 이용해서 요청함.
//                    //val deleteRequest = DeleteRequest(boardid.toString(), deleteListener)
//                    //val queue = Volley.newRequestQueue(this)
//                    //gqueue.add(deleteRequest)
//                }
//                builder.setNegativeButton("취소") { DialogInterface, i ->
//
//                }
//                builder.show()
//
//                val intent = Intent(applicationContext, BoardActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//
//            android.R.id.home -> {
//                val intent = Intent(applicationContext, BoardActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }
}
