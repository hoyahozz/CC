import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_image.view.*
import kr.ac.castcommunity.cc.R

class ViewPagerAdapter(noticeList: ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = noticeList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.notice.setImageResource(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.fragment_image, parent, false)){
        // 쉽게 이야기해서 자식 XML을 부모 XML에 담는 과정이라고 생각하면 될듯.
        // inflate(resource, root, attachToRoot)
        // 레이아웃 파일, 생성될 View의 부모를 명시, true 일 때 root의 자식 View로 자동 추가
        val notice = itemView.pager_img!!
    }
}