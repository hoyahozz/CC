package kr.ac.castcommunity.cc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.castcommunity.cc.adapters.PostAdapter;
import kr.ac.castcommunity.cc.models.Board;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView mPostRecyclerView;

    private PostAdapter mAdpater;
    private List<Board> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        mPostRecyclerView = findViewById(R.id.recyclerView);
        mDatas = new ArrayList<>();
        mDatas.add(new Board(null,"title","contents"));
        mDatas.add(new Board(null,"title","contents"));
        mDatas.add(new Board(null,"title","contents"));

        mAdpater = new PostAdapter(mDatas);
        mPostRecyclerView.setAdapter(mAdpater);
    }



}
