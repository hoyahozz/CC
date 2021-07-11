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
        holder.writer.text = data.writer
        holder.time.text = data.time
        holder.bind(position)

    }

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.item_board_title)
        val contents = itemView.findViewById<TextView>(R.id.item_board_content)
        val writer = itemView.findViewById<TextView>(R.id.item_board_writer)
        val time = itemView.findViewById<TextView>(R.id.item_board_time)

        fun bind(position: Int) {
            itemView.setOnClickListener {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra("bnum", position+1)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run {
                    context.startActivity(this)
                }
            }
        }
    }
}
