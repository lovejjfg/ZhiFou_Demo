package com.lovejjfg.zhifou.view;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovejjfg.powerrecycle.DefaultAnimator;
import com.lovejjfg.powerrecycle.SwipeRefreshRecycleView;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.base.App;
import com.lovejjfg.zhifou.data.Person;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.presenters.ListPresenter;
import com.lovejjfg.zhifou.presenters.ListPresenterImpl;
import com.lovejjfg.zhifou.presenters.ListStoryPresenterModule;
import com.lovejjfg.zhifou.ui.recycleview.OnItemClickListener;
import com.lovejjfg.zhifou.ui.recycleview.StoriesRecycleAdapter;
import com.lovejjfg.zhifou.ui.recycleview.holder.DateViewHolder;
import com.lovejjfg.zhifou.util.JumpUtils;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import static com.lovejjfg.zhifou.presenters.ListPresenter.CURRENT_DATE;
import static com.lovejjfg.zhifou.presenters.ListPresenter.SAVED_ITEMS;

public class ListStory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListPresenter.View, View.OnClickListener, OnItemClickListener, SwipeRefreshRecycleView.OnRefreshLoadMoreListener, SwipeRefreshRecycleView.OnScrollListener {
    private static final String TAG = ListStory.class.getSimpleName();
    private static final int RC_SEARCH = 11;
    private SwipeRefreshRecycleView mRecyclerView;
    @Inject
    ListPresenterImpl mMainPresenter;
    @Inject
    String name;

    @Inject
    Person person;

    private GridLayoutManager manager;
    private StoriesRecycleAdapter adapter;
    private String mDate;
    private String mTitle;
    private double lastTitlePos;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private View searchMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("首页");
        mRecyclerView = (SwipeRefreshRecycleView) findViewById(R.id.srrv);
        manager = new GridLayoutManager(this, 1);

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultAnimator());
        adapter = new StoriesRecycleAdapter(this);
        adapter.setTotalCount(50);
        adapter.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.recycler_footer_new, mRecyclerView, false));
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnScrollListener(this);
        Log.e(TAG, "onCreate: " + name);
        ListStoryComponent storyComponent = DaggerListStoryComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .listStoryPresenterModule(new ListStoryPresenterModule(this))
                .build();
        storyComponent.getName("嘻嘻嘻嘻嘻");
        storyComponent.getName("XXX");
        Log.e(TAG, "onCreate: " + name);
        storyComponent
                .inject(this);
        Log.e(TAG, "onCreate: " + person);
        Log.e(TAG, "onCreate: " + name);
//        mMainPresenter = new ListPresenterImpl(this);
        mMainPresenter.onCreate(savedInstanceState);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        toolbar.setOnClickListener(v -> {
            boolean b = UIUtils.doubleClick();
            if (b) {
                Log.e("TAG", "onClick: 双击了！！");
                mRecyclerView.getRecycle().smoothScrollToPosition(0);

            }
        });
        mRecyclerView.getRecycle().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 5) {
                    fab.hide();
                } else if (dy < -5) {
                    fab.show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView version = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_version);
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            Log.e(TAG, "onCreate:" + pi.versionName);
            version.setText(pi.versionName);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            // get the icon's location on screen to pass through to the search screen
            searchMenuView = toolbar.findViewById(R.id.action_search);
            int[] loc = new int[2];
            searchMenuView.getLocationOnScreen(loc);
            startActivityForResult(SearchActivity.createStartIntent(this, loc[0], loc[0] +
                    (searchMenuView.getWidth() / 2)), RC_SEARCH, ActivityOptions
                    .makeSceneTransitionAnimation(this).toBundle());
            searchMenuView.setAlpha(0f);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadMore(DailyStories stories) {
        adapter.appendList(stories);
        mDate = stories.getDate();
        Log.e(TAG, "onLoadMore: " + mDate);
    }

    @Override
    public void onLoad(DailyStories stories) {
        adapter.setList(stories);
        mDate = stories.getDate();
        Log.e(TAG, "onLoad: " + mDate);
        Log.e(TAG, "onLoad: " + stories.toString());
    }

    @Override
    public void onLoadError(String errorCode) {
        Log.e("TAG", "onLoadError: " + errorCode);
        adapter.loadMoreError();
    }

    @Override
    public void onReLoadItems(List<StoriesRecycleAdapter.Item> items) {
        adapter.setList(items);

    }

    @Override
    public void onReSetDate(String date) {
        Log.e("TAG", "onReSetDate: " + date);
        mDate = date;
    }

    @Override
    public void setPresenter(ListPresenter presenter) {
        Log.e(TAG, "setPresenter: ");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                JumpUtils.jumpToDataPicker(this, v);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }

    @Override
    public void isLoading(final boolean isLoading) {
        mRecyclerView.setRefresh(isLoading);
    }

    @Override
    public void isLoadingMore(final boolean loading) {
//        adapter.isLoadingMore(loading);
    }

//    @Override
//    public void onRefresh() {
//        mMainPresenter.onLoading();
//    }

    @Override
    public void onItemClick(View itemView, ImageView image, int id) {
        mMainPresenter.onItemClicked(itemView, image, id);
    }

    @Override
    public void onRefresh() {
        mMainPresenter.onLoading();
        adapter.onRefresh();
    }

    @Override
    public void onLoadMore() {
        mMainPresenter.onLoadMore(mDate);
    }

    @Override
    public void onScrolled(SwipeRefreshRecycleView recyclerView, int dx, int dy) {
        int position = manager.findFirstVisibleItemPosition();
        if (lastTitlePos == position) {
            return;
        }
        StoriesRecycleAdapter.Item item = adapter.getItem(position);
        int type = item.getType();
        if (type == StoriesRecycleAdapter.Type.TYPE_HEADER) {
            mTitle = getString(R.string.title_activity_main);
        } else if (dy > 0) {//向上
            mTitle = DateViewHolder.getDate(adapter.getTitleBeforePosition(position), ListStory.this);
//                mTitle = DateViewHolder.getDate(item.getDate(), ListStory.this);
            mTitle = DateViewHolder.getDate(adapter.getTitleAtPosition(position), ListStory.this);
            if (TextUtils.isEmpty(mTitle)) {
                return;
            }
        } else if (dy < 0) {//向下
            mTitle = DateViewHolder.getDate(adapter.getTitleBeforePosition(position), ListStory.this);
        }
        fab.layout(fab.getLeft(), fab.getTop() + dy, fab.getRight(), fab.getBottom() + dy);
        fab.requestLayout();


        ListStory.this.getSupportActionBar().setTitle(mTitle);
        lastTitlePos = position;


    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SEARCH) {
            searchMenuView.setAlpha(1.0f);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        List<StoriesRecycleAdapter.Item> list = adapter.getList();
        ArrayList<StoriesRecycleAdapter.Item> items = new ArrayList<>();
        items.addAll(list);
        outState.putParcelableArrayList(SAVED_ITEMS, items);
        outState.putString(CURRENT_DATE, mDate);
        super.onSaveInstanceState(outState);
    }
}
