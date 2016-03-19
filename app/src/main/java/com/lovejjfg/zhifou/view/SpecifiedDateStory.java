package com.lovejjfg.zhifou.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.presenters.SpecifiedDateImpl;
import com.lovejjfg.zhifou.ui.recycleview.OnItemClickListener;
import com.lovejjfg.zhifou.ui.recycleview.SpecifiedStoriesAdapter;
import com.lovejjfg.zhifou.ui.widget.SwipRefreshRecycleView;
import com.lovejjfg.zhifou.util.DateUtils;
import com.lovejjfg.zhifou.util.JumpUtils;

public class SpecifiedDateStory extends AppCompatActivity implements OnItemClickListener, SwipRefreshRecycleView.OnRefreshLoadMoreListener, SpecifiedDateView {

    private SwipRefreshRecycleView mRecycleView;
    private SpecifiedStoriesAdapter adapter;
    private SpecifiedDateImpl presenter;
    private String date;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specified_date_story);
        date = getIntent().getStringExtra(Constants.DATE);
        presenter = new SpecifiedDateImpl(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (date != null) {
        toolbar.setTitle(DateUtils.getMainPageDate(date));
        }
        mRecycleView = (SwipRefreshRecycleView) findViewById(R.id.srrv);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        adapter = new SpecifiedStoriesAdapter();
        adapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(adapter);
        mRecycleView.setOnRefreshListener(this);
//        mRecycleView.setOnScrollListener(this);
        presenter.onLoading(date);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtils.jumpToDataPickerForResult(SpecifiedDateStory.this, view, 100);
            }
        });
    }

    @Override
    public void onItemClick(View itemView, ImageView image, int id) {
        JumpUtils.jumpToDetail(this, itemView, id);
    }

    @Override
    public void onRefresh() {
        presenter.onLoading(date);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onLoadMore(DailyStories stories) {
        adapter.setList(stories);

    }

    @Override
    public void onLoadError(String errorCode) {

    }

    @Override
    public void isLoading(boolean isLoading) {
        mRecycleView.setRefresh(isLoading);
    }

    @Override
    public void isLoadingMore(boolean isLoadingMore) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            String date = data.getStringExtra(Constants.DATE);
            if (null != date) {
                presenter.onLoading(date);
                toolbar.setTitle(DateUtils.getMainPageDate(date));
            }
        }
    }
}
