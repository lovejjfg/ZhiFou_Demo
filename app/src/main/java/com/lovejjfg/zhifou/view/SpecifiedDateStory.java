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

import com.lovejjfg.powerrecycle.SwipeRefreshRecycleView;
import com.lovejjfg.sview.SupportActivity;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.base.BaseActivity;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.presenters.SpecifiedDateImpl;
import com.lovejjfg.zhifou.presenters.SpecifiedDatePresenter;
import com.lovejjfg.zhifou.ui.recycleview.OnItemClickListener;
import com.lovejjfg.zhifou.ui.recycleview.SpecifiedStoriesAdapter;
import com.lovejjfg.zhifou.util.DateUtils;
import com.lovejjfg.zhifou.util.JumpUtils;
import com.lovejjfg.zhifou.util.UIUtils;

public class SpecifiedDateStory extends SupportActivity implements OnItemClickListener, SwipeRefreshRecycleView.OnRefreshLoadMoreListener, SpecifiedDateView<SpecifiedDatePresenter,DailyStories> {

    private SwipeRefreshRecycleView mRecycleView;
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
        String showDate = getIntent().getStringExtra(Constants.SHOW_DATE);
        if (showDate != null) {
            toolbar.setTitle(DateUtils.getMainPageDate(showDate));
        }
        mRecycleView = (SwipeRefreshRecycleView) findViewById(R.id.srrv);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        adapter = new SpecifiedStoriesAdapter();
        adapter.setLoadMoreView(UIUtils.inflate(R.layout.recycler_footer_new, mRecycleView));
        adapter.setOnItemClickListener(this);
        mRecycleView.setAdapter(adapter);
        mRecycleView.setOnRefreshListener(this);
//        mRecycleView.setOnScrollListener(this);
        presenter.onLoading(date);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> JumpUtils.jumpToDataPickerForResult(SpecifiedDateStory.this, view, 100));
    }

    @Override
    public void onItemClick(View itemView, ImageView image, int id) {
        JumpUtils.jumpToDetail(this, itemView, image, id);
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
        adapter.setTotalCount(adapter.getList().size());
    }

    @Override
    public void onLoad(DailyStories stories) {

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
            String showDate = data.getStringExtra(Constants.SHOW_DATE);
            String date = data.getStringExtra(Constants.DATE);
            if (null != date) {
                this.date = date;
                presenter.onLoading(date);
                toolbar.setTitle(DateUtils.getMainPageDate(showDate));
            }
        }
    }

    @Override
    public SpecifiedDatePresenter setPresenter() {
        return null;
    }
}
