package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.util.ErrorUtil;
import com.lovejjfg.zhifou.view.SpecifiedDateView;

import rx.Subscription;

/**
 * Created by zhangjun on 2016-03-19.
 */
public class SpecifiedDateImpl implements SpecifiedDatePresenter {

    SpecifiedDateView view;
    Activity activity;

    public SpecifiedDateImpl(SpecifiedDateView view) {
        activity = (Activity) view;
        this.view = view;


    }

    @Override
    public void onLoading(String date) {

        Subscription beforeSubscribe = BaseDataManager.handleNormalService(BaseDataManager.getDailyApiService().getBeforeDailyStories(date), dailyStories -> {
            view.onLoadMore(dailyStories);
            view.isLoading(false);
        }, throwable -> {
            Log.e("TAG", "onLoading: ", throwable);
            ErrorUtil.handleError(view, throwable, true, false);
            view.isLoading(false);
        }, () -> view.isLoading(true));
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
