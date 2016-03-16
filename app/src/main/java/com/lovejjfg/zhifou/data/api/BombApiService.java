package com.lovejjfg.zhifou.data.api;

import com.lovejjfg.zhifou.data.model.DailyStories;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 张俊 on 2016/3/15.
 */
public interface BombApiService {


    @POST("/info")
    void insertInfoDb(@Body String bean, Callback<DailyStories> callback);
}
