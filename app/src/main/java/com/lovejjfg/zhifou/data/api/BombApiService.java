package com.lovejjfg.zhifou.data.api;

import com.lovejjfg.zhifou.data.model.ContactBean;
import com.lovejjfg.zhifou.data.model.ResultBean;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by 张俊 on 2016/3/15.
 */
public interface BombApiService {


    @POST("/info")
    void insertInfoDb(@Body ContactBean bean, Callback<ContactBean> callback);
    @GET("/info")
    void getDbInfos( Callback<ResultBean> callback);
}
