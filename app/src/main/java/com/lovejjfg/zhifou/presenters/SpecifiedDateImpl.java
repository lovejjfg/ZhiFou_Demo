package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.view.View;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.view.SpecifiedDateView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        view.isLoading(true);
        BaseDataManager.getDailyApiService().getBeforeDailyStories(date, new Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, Response response) {
                view.onLoadMore(dailyStories);
                view.isLoading(false);
            }

            @Override
            public void failure(RetrofitError error) {
                view.isLoading(false);
            }
        });
    }

    @Override
    public void onItemClicked(View itemView, View image, int id) {

    }
}
