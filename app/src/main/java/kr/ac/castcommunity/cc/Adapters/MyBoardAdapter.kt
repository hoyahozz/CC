package kr.ac.castcommunity.cc.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kr.ac.castcommunity.cc.DetailActivity
import kr.ac.castcommunity.cc.MyBoardActivity
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.Models.Board
import kr.ac.castcommunity.cc.Request.CommentCountRequest
import org.json.JSONException
import org.json.JSONObject

class MyBoardAdapter(val context: MyBoardActivity, val datas: ArrayList<Board>) :

    RecyclerView.Adapter<MyBoardAdapter.BoardViewHolder>() {

    // RecyclerView 에 표시될 Item View 를 생성하는 역할을 담당하는 Adapter 구현
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        // ViewHolder 를 생성
        return BoardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_my_board,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return datas.size // 아이템의 개수 조회
    }

    // adapter 가 해당 Position 에 해당하는 데이터를 결합하는 과정
    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = datas[position]
        holder.title.text = data.title
        holder.contents.text = data.content
        holder.writer.text = data.writer
        holder.time.text = data.time
        holder.cnt.text = data.cnt.toString()
        holder.bType.text = data.btype
        holder.bind(data.bnum!!.toInt())

        val commentListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                val comment = jsonObject.getInt("cnt")
                if (success == true) {
                    holder.comment.text = comment.toString()
                } else {
                    return@Listener
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val commentRequest = CommentCountRequest(
            data.bnum!!.toInt(),
            commentListener
        )
        val queue = Volley.newRequestQueue(context)
        queue.add(commentRequest)
    }

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 해당 TextView 변수 지정
        val title = itemView.findViewById<TextView>(R.id.my_item_board_title)
        val contents = itemView.findViewById<TextView>(R.id.my_item_board_content)
        val writer = itemView.findViewById<TextView>(R.id.my_item_board_writer)
        val time = itemView.findViewById<TextView>(R.id.my_item_board_time)
        val cnt = itemView.findViewById<TextView>(R.id.my_item_board_reCnt)
        val comment = itemView.findViewById<TextView>(R.id.my_item_board_coCnt)
        val bType = itemView.findViewById<TextView>(R.id.my_item_board_bType)

        fun bind(position: Int) {
            itemView.setOnClickListener {
                // 리사이클러뷰에서는 setOnItemClickListener 존재 X 직접 지정이 필요
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("bnum", position)
                context.startActivity(intent)
            }
        }
    }
}
