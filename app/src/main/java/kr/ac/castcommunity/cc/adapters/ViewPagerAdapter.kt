import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_image.view.*
import kr.ac.castcommunity.cc.R

class ViewPagerAdapter(idolList: ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = idolList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.idol.setImageResource(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.fragment_image, parent, false)){

        val idol = itemView.pager_img!!
    }
}