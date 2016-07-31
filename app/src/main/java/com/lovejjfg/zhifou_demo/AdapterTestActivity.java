package com.lovejjfg.zhifou_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

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
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    private MyRecycleAdapter adapter;

    private List<String> list;
    private Runnable refreshAction;
    private Runnable loadMoreAction;
    private ArrayList<String> arrayList;
    private boolean isRun;
    private boolean enable = true;
    private boolean own = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_test);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        mRecycleView.setPullRefreshEnable(false);
        adapter = new MyRecycleAdapter();
        adapter.setTotalCount(10);
//        adapter.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.recycler_footer_new, mRecycleView, false));
        mRecycleView.setAdapter(adapter);
        mRecycleView.setOnRefreshListener(this);
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("  ");
        }
        refreshAction = new Runnable() {
            @Override
            public void run() {
                adapter.setList(list);
                mRecycleView.setRefresh(false);
            }
        };

        loadMoreAction = new Runnable() {
            @Override
            public void run() {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("ccc1");
                arrayList.add("ccc2");
                arrayList.add("ccc3");
                arrayList.add("ccc4");
                arrayList.add("ccc5");
                adapter.appendList(arrayList);
                isRun = false;
            }
        };
        mRecycleView.setRefresh(true);
        mRecycleView.postDelayed(refreshAction, 1000);

    }

    @Override
    public void onRefresh() {
        mRecycleView.postDelayed(refreshAction, 2000);

    }

    @Override
    public void onLoadMore() {
        if (isRun) {
            return;
        }
        isRun = true;
        mRecycleView.postDelayed(loadMoreAction, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.load_more:
                adapter.setTotalCount(100);
                break;
            case R.id.own:
                adapter.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.recycler_footer_new, mRecycleView, false));
                break;
            case R.id.pull_refresh:
                enable = !enable;
                mRecycleView.setPullRefreshEnable(enable);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
