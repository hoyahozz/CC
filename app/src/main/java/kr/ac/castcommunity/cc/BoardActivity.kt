package kr.ac.castcommunity.cc

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.castcommunity.cc.Board.BoardDecoration
import kr.ac.castcommunity.cc.Toolbar.BoardToolbarActivity
import kr.ac.castcommunity.cc.adapters.BoardAdapter
import kr.ac.castcommunity.cc.models.Board
import java.util.ArrayList

class BoardActivity : BoardToolbarActivity() {

    private var mPostRecyclerView: RecyclerView? = null

    private var mAdpater: BoardAdapter? = null
    val mDatas : ArrayList<Board> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)

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

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        mPostRecyclerView!!.addItemDecoration(decoration)
    }
}
