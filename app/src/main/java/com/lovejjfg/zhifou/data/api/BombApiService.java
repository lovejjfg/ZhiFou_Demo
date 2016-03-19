package com.lovejjfg.zhifou.data.api;

import com.lovejjfg.zhifou.data.model.BatchBean;
import com.lovejjfg.zhifou.data.model.BatchResultBean;
import com.lovejjfg.zhifou.data.model.ContactBean;
import com.lovejjfg.zhifou.data.model.ResultBean;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by 张俊 on 2016/3/15.
 */
public interface BombApiService {


    @POST("/classes/info")
    void insertInfoDb(@Body ContactBean bean, Callback<ContactBean> callback);
    @GET("/classes/info")
    void getDbInfos( Callback<ResultBean> callback);
    @GET("/classes/info")
    Observable<ResultBean> getDbInfos();

    @POST("/batch")
    void insertDbInfos(@Body BatchBean bean,Callback<List<BatchResultBean>> callback);
}
