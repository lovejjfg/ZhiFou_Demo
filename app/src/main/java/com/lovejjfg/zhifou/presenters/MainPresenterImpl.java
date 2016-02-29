package com.lovejjfg.zhifou.presenters;


import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainPresenterImpl implements MainPresenter, LifecycleCallbacks {
    View mView;
    boolean isLoadingMore;
    boolean isLoading;

    public MainPresenterImpl(View view) {
        this.mView = view;
        onLoading();
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onItemClicked(int position) {

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
