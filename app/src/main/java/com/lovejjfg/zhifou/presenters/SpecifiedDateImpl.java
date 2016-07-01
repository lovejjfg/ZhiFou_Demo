package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.view.View;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.view.SpecifiedDateView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<DailyStories> beforeDailyStories =
                BaseDataManager.getDailyApiService().getBeforeDailyStories(date);
        beforeDailyStories.enqueue(new Callback<DailyStories>() {
            @Override
            public void onResponse(Call<DailyStories> call, Response<DailyStories> response) {
                DailyStories body = response.body();
                view.onLoadMore(body);
                view.isLoading(false);
            }

            @Override
            public void onFailure(Call<DailyStories> call, Throwable t) {
                view.isLoading(false);
            }
        });
    }

    @Override
    public void onItemClicked(View itemView, View image, int id) {

    }
}
