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
import kr.ac.castcommunity.cc.models.Comment
import org.w3c.dom.Text

class CommentAdapter(val context : Context, val datas: ArrayList<Comment>) :


    RecyclerView.Adapter<CommentAdapter.BoardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        // ViewHolder 를 생성
        return BoardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size // 아이템의 개수 조회
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = datas[position]
        holder.contents.text = data.contents
        holder.writer.text = data.writer
        holder.time.text = data.time

    }

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contents = itemView.findViewById<TextView>(R.id.item_comment_content)
        val writer = itemView.findViewById<TextView>(R.id.item_comment_writer)
        val time = itemView.findViewById<TextView>(R.id.item_comment_time)
    }
}
