package com.lovejjfg.zhifou.presenters;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.util.WebUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangjun on 2016-03-01.
 */
public class DetailPresenterImpl extends BasePresenterImpl implements DetailPresenter {

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
            BaseDataManager.getDailyApiService().getStoryDetail(String.valueOf(id))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                        view.isLoading(true);
                        isLoading = true;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(story -> {
                        isLoading = false;
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
                    }, throwable -> {
                        isLoading = false;
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
        super.onDestroy();
        activity = null;
        view = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }
}
