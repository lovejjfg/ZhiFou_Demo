package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.util.WebUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zhangjun on 2016-03-01.
 */
public class DetailPresenterImpl implements DetailPresenter {

    private boolean isLoading;
    View view;
    Activity activity;
//    private final ExecutorService service;

    public DetailPresenterImpl(View view) {
        this.view = view;
        activity = (Activity) view;
//        service = Executors.newCachedThreadPool();

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoading(int id) {

        if (!isLoading) {
            view.isLoading(true);
            isLoading = true;
            BaseDataManager.getDailyApiService().getStoryDetail(String.valueOf(id), new Callback<Story>() {
                @Override
                public void success(Story story, Response response) {
                    if (!TextUtils.isEmpty(story.getImage())) {
                        view.onBindImage(story.getImage());
                    }
                    if (!TextUtils.isEmpty(story.getBody())) {
                        String data = WebUtils.BuildHtmlWithCss(story.getBody(), story.getCssList(), false);
                        view.onBindWebView(data);
                    }
                    if (!TextUtils.isEmpty(story.getTitle())) {
                        view.onBindTittle(story.getTitle());
                    }
                }

                @Override
                public void failure(RetrofitError error) {

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
    public void onDestroy() {
        Log.i("TAG", "onDestroy: xxxxxxx");
//        service.shutdownNow();
        activity = null;
        view = null;

    }
}
