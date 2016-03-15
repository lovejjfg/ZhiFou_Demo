package com.lovejjfg.zhifou.data;

import com.google.gson.GsonBuilder;
import com.lovejjfg.zhifou.BuildConfig;
import com.lovejjfg.zhifou.data.api.BombApiService;
import com.lovejjfg.zhifou.data.api.DailyApiService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by lovejjfg on 2016/2/21.
 * <p/>
 * 用于统一管理api请求
 */
public class BaseDataManager {
    private static final String API = "http://news.at.zhihu.com/api/4";
    private static final String URL_INSERT = "https://api.bmob.cn/1/classes/info";
    private static RestAdapter restAdapter;

    private static <T> T createApi(Class<T> clazz, String api) {
        if (restAdapter == null) {
            synchronized (BaseDataManager.class) {
                if (restAdapter == null) {
                    restAdapter = new RestAdapter.Builder()
                            .setEndpoint(api)
                            .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                            .build();
                }
            }
        }
        return restAdapter.create(clazz);
    }

    public static DailyApiService getDailyApiService() {
        return createApi(DailyApiService.class, API);
    }
    public static BombApiService getBombApiService() {
        //// TODO: 2016/3/15 相关api的封装
        return createApi(BombApiService.class, URL_INSERT);
    }




}
