package kr.ac.castcommunity.cc.Adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kr.ac.castcommunity.cc.DetailActivity
import kr.ac.castcommunity.cc.MessageDetailActivity
import kr.ac.castcommunity.cc.Models.Board
import kr.ac.castcommunity.cc.Models.Message
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.Request.CommentCountRequest
import org.json.JSONException
import org.json.JSONObject


// 쪽지 메인 어댑터
class MessageRoomAdapter(val context: FragmentActivity?, val datas: ArrayList<Message>) :

    RecyclerView.Adapter<MessageRoomAdapter.BoardViewHolder>() {

    // RecyclerView 에 표시될 Item View 를 생성하는 역할을 담당하는 Adapter 구현
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        // ViewHolder 를 생성
        return BoardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_message_room,
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
        val pref: SharedPreferences? = context?.getSharedPreferences(
            "mine",
            Context.MODE_PRIVATE
        ) // SharedPreferences 초기화

        val my_nick = pref?.getString("nickname", "").toString()

        if(data.receinick == my_nick) {
            holder.receinick.text = data.sendnick
        } else {
            holder.receinick.text = data.receinick
        }
        holder.date.text = data.date
        holder.content.text = data.content
        holder.bind(data.messageroom!!.toInt())

    }

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 해당 TextView 변수 지정
        val receinick = itemView.findViewById<TextView>(R.id.message_room_nickname)
        val date = itemView.findViewById<TextView>(R.id.message_room_date)
        val content = itemView.findViewById<TextView>(R.id.message_room_content)

        fun bind(position: Int) {
            itemView.setOnClickListener {
                // 리사이클러뷰에서는 setOnItemClickListener 존재 X 직접 지정이 필요
                Intent(context, MessageDetailActivity::class.java).apply {
                    putExtra("messageroom", position)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 기존 플래그에 새로운 플래그 추가, 새로운 태스크를 생성하여 태스크 안에 액티비티를 추가한다.

                }.run {
                    context?.startActivity(this)
                }
            }
        }

    }
}
