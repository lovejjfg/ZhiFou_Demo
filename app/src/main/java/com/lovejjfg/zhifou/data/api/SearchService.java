package com.lovejjfg.zhifou.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by zhangjun on 2016-03-19.
 */
public interface SearchService {
    @GET("/search/{query}")
    Call<String> getSearchInfo(@Path("query") String query);
}
