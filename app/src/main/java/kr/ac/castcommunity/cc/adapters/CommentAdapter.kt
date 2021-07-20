package kr.ac.castcommunity.cc.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kr.ac.castcommunity.cc.DetailActivity
import kr.ac.castcommunity.cc.R
import kr.ac.castcommunity.cc.models.Comment
import kr.ac.castcommunity.cc.request.CommentDeleteRequest
import org.json.JSONException
import org.json.JSONObject

class CommentAdapter(val context: Context, val datas: ArrayList<Comment>, val memId: String) :


    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        // ViewHolder 를 생성
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return datas.size // 아이템의 개수 조회
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        val data = datas[position]
        holder.contents.text = data.content
        holder.writer.text = data.writer
        holder.time.text = data.time

        if (memId == data.memId) {
            holder.delete.isVisible = true
        } else {
            holder.delete.isVisible = false
        }

        holder.delete.setOnClickListener {
            builder.setMessage("정말로 삭제하시겠습니까?")
            builder.setPositiveButton("확인") { DialogInterface, i ->
                val deleteListener = Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success == true) { // 글 삭제에 성공했을 때
                            Toast.makeText(context, "삭제 완료!", Toast.LENGTH_LONG)
                                .show()
                            Intent(context, DetailActivity::class.java).apply {
                                putExtra("bnum", data.boardid)
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 기존 플래그에 새로운 플래그 추가, 새로운 태스크를 생성하여 태스크 안에 액티비티를 추가한다.
                            }.run {
                                context.startActivity(this)
                            }
                        } else {
                            // 글 삭제에 실패했을 때
                            Toast.makeText(context, "삭제 실패!", Toast.LENGTH_LONG).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                // 서버로 Volley를 이용해서 요청함.
                val deleteRequest = CommentDeleteRequest(
                    data.memId.toString(),
                    data.commentid.toString(),
                    deleteListener
                )
                val queue = Volley.newRequestQueue(context)
                queue.add(deleteRequest)
            }
            builder.setNegativeButton("취소") { DialogInterface, i ->

            }
            builder.show()
        }
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contents = itemView.findViewById<TextView>(R.id.item_comment_content)
        val writer = itemView.findViewById<TextView>(R.id.item_comment_writer)
        val time = itemView.findViewById<TextView>(R.id.item_comment_time)
        val delete = itemView.findViewById<ImageButton>(R.id.comment_delete)
    }

}
