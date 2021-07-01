package kr.ac.castcommunity.cc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.models.Board
import org.w3c.dom.Text

class BoardAdapter(val context : Context, val datas: ArrayList<Board>) :

    RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        // ViewHolder 를 생성
        return BoardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size // 아이템의 개수 조회
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = datas[position]
        holder.title.text = data.title
        holder.contents.text = data.contents
        holder.time.text = data.time
        holder.writer.text = data.writer
    }


    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_board_title)
        val contents = itemView.findViewById<TextView>(R.id.item_board_content)
        val writer = itemView.findViewById<TextView>(R.id.item_board_writer)
        val time = itemView.findViewById<TextView>(R.id.item_board_time)

    }
}
