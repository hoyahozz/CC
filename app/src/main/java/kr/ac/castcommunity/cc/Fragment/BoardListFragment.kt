package kr.ac.castcommunity.cc.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kr.ac.castcommunity.cc.BoardActivity
import kr.ac.castcommunity.cc.MyBoardActivity
import kr.ac.castcommunity.cc.R

class BoardListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pref: SharedPreferences? = this.activity?.getSharedPreferences("board", Context.MODE_PRIVATE) // SharedPreferences 초기화
        val editor: SharedPreferences.Editor =
            pref!!.edit() //  SharedPreferences 의 데이터를 저장/편집하기 위한 Editor 변수 선언
        editor.clear()

        val view = inflater.inflate(R.layout.fragment_boardlist, container, false)

        val list_board1 = view.findViewById<Button>(R.id.list_board1) // 내가 쓴 글
        val list_board2 = view.findViewById<Button>(R.id.list_board2) // 댓글 쓴 글
        val list_board3 = view.findViewById<Button>(R.id.list_board3) // 자유게시판
        val list_board4 = view.findViewById<Button>(R.id.list_board4) // 정보게시판
        val list_board5 = view.findViewById<Button>(R.id.list_board5) // 장터
        val list_board6 = view.findViewById<Button>(R.id.list_board6) // 퇴사자게시판
        val list_board7 = view.findViewById<Button>(R.id.list_board7) // 동아리

        list_board1.setOnClickListener {
            val intent = Intent(this.activity, MyBoardActivity::class.java)
            startActivity(intent)
        }

        list_board3.setOnClickListener {
            editor.putString("btype", list_board3.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        list_board4.setOnClickListener {
            editor.putString("btype", list_board4.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        list_board5.setOnClickListener {
            editor.putString("btype", list_board5.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        list_board6.setOnClickListener {
            editor.putString("btype", list_board6.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }

        list_board7.setOnClickListener {
            editor.putString("btype", list_board7.text.toString())
            editor.commit()
            val intent = Intent(this.activity, BoardActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}