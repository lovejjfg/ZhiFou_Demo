/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.lovejjfg.zhifou_demo.mvp.presenters;

import com.lovejjfg.zhifou_demo.data.BaseDataManager;
import com.lovejjfg.zhifou_demo.data.model.DailyStories;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainPresenterImpl implements MainPresenter, LifecycleCallbacks {
    View mView;
    boolean isLoadingMore;
    boolean isLoading;

    public MainPresenterImpl(View view) {
        this.mView = view;
        initData();
    }

    private void initData() {
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
