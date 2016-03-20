package com.lovejjfg.zhifou.data;

import com.google.gson.GsonBuilder;
import com.lovejjfg.zhifou.BuildConfig;
import com.lovejjfg.zhifou.data.api.BombApiService;
import com.lovejjfg.zhifou.data.api.DailyApiService;
import com.lovejjfg.zhifou.data.api.SearchService;
import com.squareup.okhttp.MediaType;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * Created by lovejjfg on 2016/2/21.
 * <p/>
 * 用于统一管理api请求
 */
public class BaseDataManager {
    private static final String API = "http://news.at.zhihu.com/api/4";
    private static final String BOMB_API = "https://api.bmob.cn/1";
    private static final String SEARCH_API = "http://zhihu-daily-purify.herokuapp.com";
    /*bomb*/
    private static final String URL_CONTACT = "https://api.bmob.cn/1/classes/Contacts";
    private static final String APPLICATION_ID = "f090e25bef0697ae9a8d2f06d08c0dad";
    private static final String API_KEY = "b55f225809b92ba6093a2b69a39f38f8";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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
    public static SearchService getSearchService() {
        return new RestAdapter.Builder()
                .setEndpoint(SEARCH_API)
//                .setConverter(new GsonConverter().p)
//                .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build()
                .create(SearchService.class);
    }
    public static BombApiService getBombApiService() {
        //// TODO: 2016/3/15 相关api的封装
        return new RestAdapter.Builder()
                .setEndpoint(BOMB_API)
//                .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader("X-Bmob-Application-Id", APPLICATION_ID);
                        request.addHeader("X-Bmob-REST-API-Key", API_KEY);
                    }
                })
                .build()
                .create(BombApiService.class);
    }




}
