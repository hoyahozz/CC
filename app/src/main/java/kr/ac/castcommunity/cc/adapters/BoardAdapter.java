package kr.ac.castcommunity.cc.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.ac.castcommunity.cc.R;
import kr.ac.castcommunity.cc.models.Board;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private List<Board>  datas;

     public BoardAdapter(List<Board> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder 를 생성
         return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        // ViewHolder 가 재활용 될 때 사용되는 메소드
        Board data = datas.get(position);
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
    }

    @Override
    public int getItemCount() {
        return datas.size(); // 아이템의 개수 조회
    }

    class BoardViewHolder extends RecyclerView.ViewHolder {
            // ViewHolder 에 필요한 데이터들을 적음.
         private TextView title;
         private TextView contents;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            // 아이템 뷰에 필요한 View
            title = itemView.findViewById(R.id.item_board_title);
            contents = itemView.findViewById(R.id.item_board_content);
        }
    }
}
