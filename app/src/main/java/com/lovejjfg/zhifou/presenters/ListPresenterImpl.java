package com.lovejjfg.zhifou.presenters;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.view.DetailStory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListPresenterImpl implements ListPresenter, LifecycleCallbacks {
    View mView;
    Activity activity;
    boolean isLoadingMore;
    boolean isLoading;

    public ListPresenterImpl(View view) {
        this.mView = view;
        this.activity = (Activity) view;
        onLoading();
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClicked(android.view.View itemView, android.view.View image, int id) {
        Intent i = new Intent(activity, DetailStory.class);
        i.putExtra(Constants.ID, id);
        final ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(itemView,
                                activity.getResources().getString(R.string.detail_view))
                        );
        activity.startActivity(i, options.toBundle());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoading() {
        if (!isLoading) {
            mView.isLoading(true);
            isLoading = true;
            BaseDataManager.getDailyApiService().getLatestDailyStories(new Callback<DailyStories>() {
                @Override
                public void success(DailyStories dailyStories, Response response) {
                    mView.onLoadMore(dailyStories);
                    mView.isLoading(false);
                    isLoading = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    mView.onLoadError(error.toString());
                    mView.isLoading(false);
                    isLoading = false;
                }
            });

        }
    }

    @Override
    public void onLoadMore(String date) {
        if (!isLoadingMore) {
            mView.isLoadingMore(true);
            isLoadingMore = true;
            BaseDataManager.getDailyApiService().getBeforeDailyStories(date, new Callback<DailyStories>() {
                @Override
                public void success(DailyStories dailyStories, Response response) {
                    mView.onLoadMore(dailyStories);
                    mView.isLoadingMore(false);
                    isLoadingMore = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    mView.onLoadError(error.toString());
                    isLoadingMore = false;
                }
            });
        }


    }
}
