package com.lovejjfg.zhifou.presenters;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.BmobUtil;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.SearchResult;
import com.lovejjfg.zhifou.view.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangjun on 2016-03-19.
 */
public class SearchImpl implements SearchPresenter {
    private static final String CHARSET = "UTF-8";
    private SearchView view;
    private String info;
    private static final String URL = "http://zhihu-daily-purify.herokuapp.com//search/";


    public SearchImpl(SearchView view) {
        this.view = view;
    }

    @Override
    public void searchFor(final String query) {
        Observable.create(new Observable.OnSubscribe<List<SearchResult>>() {


            @Override
            public void call(Subscriber<? super List<SearchResult>> subscriber) {
                try {
                    info = URLEncoder.encode(query, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                List<SearchResult> searchResult = BmobUtil.getSearchResult(URL+info);
                if (null == searchResult) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(searchResult);
                }
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchResult>>() {
                    @Override
                    public void onCompleted() {
                        view.onSearchEmpt();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onSearchEmpt();
                    }

                    @Override
                    public void onNext(List<SearchResult> list) {
                        view.onSearchOut(list);
                    }
                });
    }
}
