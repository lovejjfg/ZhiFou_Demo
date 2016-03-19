package com.lovejjfg.zhifou.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.BmobUtil;
import com.lovejjfg.zhifou.data.ContactUtils;
import com.lovejjfg.zhifou.data.model.BatchBean;
import com.lovejjfg.zhifou.data.model.BatchResultBean;
import com.lovejjfg.zhifou.data.model.ContactBean;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.ResultBean;
import com.lovejjfg.zhifou.presenters.ListPresenter;
import com.lovejjfg.zhifou.presenters.ListPresenterImpl;
import com.lovejjfg.zhifou.ui.recycleview.OnItemClickListener;
import com.lovejjfg.zhifou.ui.recycleview.StoriesAdapter;
import com.lovejjfg.zhifou.ui.recycleview.holder.DateViewHolder;
import com.lovejjfg.zhifou.ui.widget.SwipRefreshRecycleView;
import com.lovejjfg.zhifou.util.JumpUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ListStory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListPresenter.View, View.OnClickListener, OnItemClickListener, SwipRefreshRecycleView.OnRefreshLoadMoreListener, SwipRefreshRecycleView.OnScrollListener {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private SwipRefreshRecycleView mRecyclerView;
    private ListPresenterImpl mMainPresenter;
    private GridLayoutManager manager;
    private StoriesAdapter adapter;
    private String mDate;
    //    private SwipeRefreshLayout mSwip;
    private String mTitle;
    private double lastTitlePos;
    private FloatingActionButton fab;
    private ProgressBar bar;
    private int i;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (SwipRefreshRecycleView) findViewById(R.id.srrv);
        manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new StoriesAdapter();
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnScrollListener(this);

        mMainPresenter = new ListPresenterImpl(this);
        dialog = new Dialog(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_bar, null);
        bar = (ProgressBar) inflate.findViewById(R.id.pb);


        dialog.addContentView(inflate, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {//获取数据库的相关信息
            dialog.show();
            i = 0;
            BaseDataManager.getBombApiService().getDbInfos()
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<ResultBean, Observable<ResultBean.ResultsEntity>>() {
                        @Override
                        public Observable<ResultBean.ResultsEntity> call(ResultBean resultBean) {
                            bar.setMax(resultBean.getResults().size());
                            return Observable.from(resultBean.getResults());//这里出去的就是一个一个的用户信息了！
                        }
                    })
                    .filter(new Func1<ResultBean.ResultsEntity, Boolean>() {
                        @Override
                        public Boolean call(ResultBean.ResultsEntity resultsEntity) {
                            return resultsEntity.getName() != null;
                        }
                    })
                    .observeOn(Schedulers.io())
                    .map(new Func1<ResultBean.ResultsEntity, Integer>() {
                        @Override
                        public Integer call(ResultBean.ResultsEntity resultsEntity) {
                            try {
                                ContactUtils.addContact2(ListStory.this, resultsEntity);
                                return ++i;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }


                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer resultsEntity) {
                            bar.setProgress(resultsEntity);
                            Log.e("插入：", resultsEntity.toString());
                        }
                    });

        } else if (id == R.id.nav_slideshow) {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission_group.CONTACTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                new Thread() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        try {
                            List<ContactBean> contacts = ContactUtils.getContacts(ListStory.this);
                            for (ContactBean bean : contacts) {
                                BmobUtil.sendContact(ListStory.this, bean, new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {
                                        response.body().close();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
            }


        } else if (id == R.id.nav_manage) {
//            /**
//             * 单个插入
//             */
//            Log.e("请求开始了！！", "");
//            ContactBean bean = new ContactBean();
//            bean.setMobile("999999");
//            bean.setName("zhangsan");
//            BaseDataManager.getBombApiService().insertInfoDb(bean, new retrofit.Callback<ContactBean>() {
//                @Override
//                public void success(ContactBean dailyStories, retrofit.client.Response response) {
//                    Log.e("插入表", "成功！！！" + dailyStories.toString());
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.e("插入表", "失败！！！" + error.getMessage());
//                }
//            });

            /**
             * 批量插入
             */
            BatchBean batchBean = new BatchBean();
            final BatchBean.RequestsEntity entity = new BatchBean.RequestsEntity();
            entity.setPath("/1/classes/Contacts");
            entity.setMethod("POST");
            entity.setBody(new BatchBean.RequestsEntity.BodyEntity("zhangwang", "136474"));

            ArrayList<BatchBean.RequestsEntity> beans = new ArrayList<>();
            beans.add(entity);
            batchBean.setRequests(beans);
            BaseDataManager.getBombApiService().insertDbInfos(batchBean, new retrofit.Callback<List<BatchResultBean>>() {
                @Override
                public void success(List<BatchResultBean> batchResultBeans, retrofit.client.Response response) {
                    Log.e("Success", batchResultBeans.get(0).toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Failed", error.getMessage());
                }
            });

        } else if (id == R.id.nav_share)

        {

        } else if (id == R.id.nav_send)

        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoadMore(DailyStories stories) {
        adapter.appendList(stories);
        mDate = stories.getDate();
    }

    @Override
    public void onLoadError(String errorCode) {

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
    public void isLoadingMore(boolean loading) {
        adapter.isLoadingMore(loading);
        adapter.notifyItemChanged(adapter.getItemCount());
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
    }

    @Override
    public void onLoadMore() {
        mMainPresenter.onLoadMore(mDate);
    }

    @Override
    public void onScrolled(SwipRefreshRecycleView recyclerView, int dx, int dy) {
        int position = manager.findFirstVisibleItemPosition();
        if (lastTitlePos == position) {
            return;
        }
        StoriesAdapter.Item item = adapter.getItem(position);
        int type = item.getType();
        if (type == StoriesAdapter.Type.TYPE_HEADER) {
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


}
