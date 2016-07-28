package com.lovejjfg.zhifou_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.ui.recycleview.MyRecycleAdapter;
import com.lovejjfg.zhifou.ui.recycleview.SwipRefreshRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdapterTestActivity extends AppCompatActivity implements SwipRefreshRecycleView.OnRefreshLoadMoreListener {
    @Bind(R.id.recycle_view)
    SwipRefreshRecycleView mRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_test);
        ButterKnife.bind(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setPullRefreshEnable(false);
        MyRecycleAdapter myRecycleAdapter = new MyRecycleAdapter();
        myRecycleAdapter.setTotalCount(10);
        myRecycleAdapter.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.recycler_footer_new, mRecycleView, false));
        mRecycleView.setAdapter(myRecycleAdapter);
        mRecycleView.setOnRefreshListener(this);
        List<String> list = new ArrayList<>();
        for (int i=0;i<50;i++) {
            list.add("  ");
        }
        myRecycleAdapter.setList(list);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
