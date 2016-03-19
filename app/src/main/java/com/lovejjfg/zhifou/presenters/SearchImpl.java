package com.lovejjfg.zhifou.presenters;

import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.view.SearchView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zhangjun on 2016-03-19.
 */
public class SearchImpl implements SearchPresenter {
    private static final String CHARSET = "UTF-8";
    private SearchView view;

    public SearchImpl(SearchView view) {
        this.view = view;
    }

    @Override
    public void searchFor(String query) {
        String info = null;
        try {
            info = URLEncoder.encode(query, CHARSET);
            BaseDataManager.getSearchService().getSearchInfo(query, new Callback<DailyStories>() {
                @Override
                public void success(DailyStories dailyStories, Response response) {
                    view.onSearchOut(dailyStories);
                }

                @Override
                public void failure(RetrofitError error) {
                    view.onSearchEmpt();
                    Log.e("erro", error.getMessage());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
