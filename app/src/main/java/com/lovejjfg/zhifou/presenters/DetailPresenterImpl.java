package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.util.WebUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            Call<Story> storyDetail =
                    BaseDataManager.getDailyApiService().getStoryDetail(String.valueOf(id));
            storyDetail.enqueue(new Callback<Story>() {
                @Override
                public void onResponse(Call<Story> call, Response<Story> response) {
                    Story story = response.body();
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
                public void onFailure(Call<Story> call, Throwable t) {

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
