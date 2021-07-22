package kr.ac.castcommunity.cc.Adapters

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kr.ac.castcommunity.cc.Models.Message
import kr.ac.castcommunity.cc.R


// 쪽지 메인 어댑터
class MessageDetailAdapter(val context: FragmentActivity?, val datas: ArrayList<Message>) :

    RecyclerView.Adapter<MessageDetailAdapter.BoardViewHolder>() {

    // RecyclerView 에 표시될 Item View 를 생성하는 역할을 담당하는 Adapter 구현
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        // ViewHolder 를 생성
        return BoardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_message_detail,
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
            holder.mType.text = "받은 쪽지"
        } else {
            holder.mType.text = "보낸 쪽지"
            holder.mType.setTextColor(Color.parseColor("#FFEB3B"))
        }
        holder.date.text = data.date
        holder.content.text = data.content

    }

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 해당 TextView 변수 지정
        val mType = itemView.findViewById<TextView>(R.id.message_detail_mType)
        val date = itemView.findViewById<TextView>(R.id.message_detail_date)
        val content = itemView.findViewById<TextView>(R.id.message_detail_content)
    }
}
