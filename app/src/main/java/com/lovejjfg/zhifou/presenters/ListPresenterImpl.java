package com.lovejjfg.zhifou.presenters;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.api.DailyApiService;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.StoriesRecycleAdapter;
import com.lovejjfg.zhifou.util.JumpUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ListPresenterImpl extends BasePresenterImpl implements ListPresenter {
    View mView;
    Activity activity;
    boolean isLoadingMore;
    boolean isLoading;
    ArrayList<Story> stories;
    private DailyApiService dailyApiService;

    @Inject
    public ListPresenterImpl(View view, DailyApiService dailyApiService, @Named("number") int number, String name, @Named("age") int age) {
        this.mView = view;
        this.activity = (Activity) view;
        this.dailyApiService = dailyApiService;
        Log.e("TAG", "ListPresenterImpl: " + number);
        Log.e("TAG", "ListPresenterImpl: " + name);
        Log.e("TAG", "ListPresenterImpl: " + age);
    }


    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            onLoading();
        } else {
            List<StoriesRecycleAdapter.Item> items = savedInstanceState.getParcelableArrayList(SAVED_ITEMS);
            String date = savedInstanceState.getString(CURRENT_DATE);
            if (items != null && items.size() > 0) {
                mView.onReLoadItems(items);
                mView.onReSetDate(date);
            } else {
                onLoading();
            }
        }
    }

    @Override
    public void onItemClicked(android.view.View itemView, android.view.View image, int id) {
        JumpUtils.jumpToDetail(activity, itemView, image, id);
    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void jumpToDetail(android.view.View itemView, int id) {
//        Intent i = new Intent(activity, DetailStory.class);
//        i.putExtra(Constants.ID, id);
//        final ActivityOptions options =
//                ActivityOptions.makeSceneTransitionAnimation(activity,
//                        Pair.create(itemView,
//                                activity.getResources().getString(R.string.detail_view))
//                );
//        activity.startActivity(i, options.toBundle());
//    }


    @Override
    public void onRefresh() {

    }

    // TODO: 2016/12/26 封装重复的逻辑
    // 1、错误异常的统一处理封装
    // 2、弹框 Toast的统一封装
    // 3、相关回调的统一封装
    @Override
    public void onLoading() {

        if (!isLoading) {
//            dailyApiService = BaseDataManager.getDailyApiService();
            Subscription listSubscribe = dailyApiService.getLatestDailyStories()
                    .subscribeOn(Schedulers.io())//事件产生在子线程
                    .doOnSubscribe(() -> {
                        mView.isLoading(true);
                        isLoading = true;
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//配合doOnSubscribe子线程

                    .observeOn(AndroidSchedulers.mainThread())//订阅在主线程
                    .subscribe(dailyStories -> {
                        mView.onLoad(dailyStories);
                        refreshView();
                    }, e -> refreshView(), this::refreshView);
            subscribe(listSubscribe);
            Log.e("TAG", "onLoading: " + listSubscribe);
        }
    }

    private void refreshView() {
        mView.isLoading(false);
        isLoading = false;
    }


    @Override
    public void onLoadMore(String date) {
        if (!isLoadingMore) {
            Subscription beforeSubscribe = dailyApiService.getBeforeDailyStories(date)
                    .subscribeOn(Schedulers.io())//事件产生在子线程
                    .doOnSubscribe(() -> {
                        mView.isLoadingMore(true);
                        isLoadingMore = true;
                        Log.e("TAG", "call: true");
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dailyStories -> {
                        mView.onLoadMore(dailyStories);
                        mView.isLoadingMore(false);
                        Log.e("TAG", "call: false");
                        isLoadingMore = false;
                    }, throwable -> {
                        mView.onLoadError(throwable.toString());
                        isLoadingMore = false;
                    });
            subscribe(beforeSubscribe);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
