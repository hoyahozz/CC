package kr.ac.castcommunity.cc.Decoration

import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainBoardDecoration(private val divHeight: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    // RecyclerView 내부에 있는 추상클래스로, 아이템을 꾸미는 역할을 맡음.
    @Override
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // top, bottom 사이에 높이 지정, 아이템 간의 구분자를 지정하는 과정.
        outRect.top = divHeight
        outRect.bottom = divHeight
    }
}