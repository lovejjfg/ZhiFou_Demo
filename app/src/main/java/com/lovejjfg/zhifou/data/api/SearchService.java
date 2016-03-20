package com.lovejjfg.zhifou.data.api;

import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.SearchResult;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by zhangjun on 2016-03-19.
 */
public interface SearchService {
    @GET("/search/{query}")
    void getSearchInfo(@Path("query") String query, Callback<String> callback);
}
