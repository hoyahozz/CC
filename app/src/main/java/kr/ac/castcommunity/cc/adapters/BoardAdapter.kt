package kr.ac.castcommunity.cc.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.ac.castcommunity.cc.DetailActivity
import kr.ac.castcommunity.cc.MainActivity

import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.Toolbar.DetailToolbarActivity
import kr.ac.castcommunity.cc.models.Board
import org.w3c.dom.Text

class BoardAdapter(val context : Context, val datas: ArrayList<Board>) :

    RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    // RecyclerView 에 표시될 Item View 를 생성하는 역할을 담당하는 Adapter 구현
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        // ViewHolder 를 생성
        return BoardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size // 아이템의 개수 조회
    }

    // adapter 가 해당 Position 에 해당하는 데이터를 결합하는 과정
    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = datas[position]
        holder.title.text = data.title
        holder.contents.text = data.contents
        holder.writer.text = data.writer
        holder.time.text = data.time
        holder.bind(position)

    }

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 해당 TextView 변수 지정
        val title = itemView.findViewById<TextView>(R.id.item_board_title)
        val contents = itemView.findViewById<TextView>(R.id.item_board_content)
        val writer = itemView.findViewById<TextView>(R.id.item_board_writer)
        val time = itemView.findViewById<TextView>(R.id.item_board_time)

        fun bind(position: Int) {
            itemView.setOnClickListener { // 리사이클러뷰에서는 setOnItemClickListener 존재 X 직접 지정이 필요
                Intent(context, DetailActivity::class.java).apply {
                    putExtra("bnum", position+1) // 0부터 시작되기 때문에 1을 더해줌
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 기존 플래그에 새로운 플래그 추가, 새로운 태스크를 생성하여 태스크 안에 액티비티를 추가한다.

                }.run {
                    context.startActivity(this)
                }
            }
        }
    }
}
