package com.lovejjfg.zhifou.data;

import com.lovejjfg.zhifou.data.api.DailyApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lovejjfg on 2016/2/21.
 * <p/>
 * 用于统一管理api请求
 */
public class BaseDataManager {
    private static final String API = "http://news.at.zhihu.com/api/4/";
    private static final String BOMB_API = "https://api.bmob.cn/1/";
    private static final String SEARCH_API = "http://zhihu-daily-purify.herokuapp.com";
    /*bomb*/
    private static final String URL_CONTACT = "https://api.bmob.cn/1/classes/Contacts/";
    private static final String APPLICATION_ID = "f090e25bef0697ae9a8d2f06d08c0dad";
    private static final String API_KEY = "b55f225809b92ba6093a2b69a39f38f8";
//    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static Retrofit userApi;
    // TODO: 2016-03-21 完成Retrofit2.0的升级，这个是什么情况

    private static <T> T createApi(Class<T> clazz) {
        if (userApi == null) {
            userApi = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .addInterceptor(chain -> chain.proceed(RequestUtils.createJustJsonRequest(chain.request())))
                            .build())
                    .build();
        }
        return userApi.create(clazz);

//        if (restAdapter == null) {
//            synchronized (BaseDataManager.class) {
//                if (restAdapter == null) {
//                    restAdapter = new RestAdapter.Builder()
//                            .setEndpoint(api)
//                            .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
//                            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
//                            .build();
//                }
//            }
//        }
//        return restAdapter.create(clazz);
    }

    public static DailyApiService getDailyApiService() {
        return createApi(DailyApiService.class);
    }
//    public static BombApiService getBombApiService() {
//        return new RestAdapter.Builder()
//                .setEndpoint(BOMB_API)
////                .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
//                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
//                .setRequestInterceptor(new RequestInterceptor() {
//                    @Override
//                    public void intercept(RequestFacade request) {
//                        request.addHeader("Content-Type", "application/json");
//                        request.addHeader("X-Bmob-Application-Id", APPLICATION_ID);
//                        request.addHeader("X-Bmob-REST-API-Key", API_KEY);
//                    }
//                })
//                .build()
//                .create(BombApiService.class);
//    }




}
