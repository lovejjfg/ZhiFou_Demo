package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.view.SpecifiedDateView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhangjun on 2016-03-19.
 */
public class SpecifiedDateImpl implements SpecifiedDatePresenter, BasePresenter {

    SpecifiedDateView view;
    Activity activity;

    public SpecifiedDateImpl(SpecifiedDateView view) {
        activity = (Activity) view;
        this.view = view;


    }

    @Override
    public void onLoading(String date) {

        Subscription beforeSubscribe = BaseDataManager.getDailyApiService().getBeforeDailyStories(date)
                .subscribeOn(Schedulers.io())//事件产生在子线程
                .doOnSubscribe(() -> view.isLoading(true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyStories -> {
                    view.onLoadMore(dailyStories);
                    view.isLoading(false);
                }, throwable -> {
                    view.isLoading(false);
                });
        subscribe(beforeSubscribe);

    }

    @Override
    public void onItemClicked(View itemView, View image, int id) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void subscribe(Subscription subscriber) {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }
}
