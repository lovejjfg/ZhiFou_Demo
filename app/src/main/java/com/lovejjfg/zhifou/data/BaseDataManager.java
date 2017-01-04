package com.lovejjfg.zhifou.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.lovejjfg.zhifou.base.App;
import com.lovejjfg.zhifou.data.api.DailyApiService;
import com.lovejjfg.zhifou.util.LoggingInterceptor;
import com.lovejjfg.zhifou.util.NetWorkUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private static final String TAG = "TAG";
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
//            CacheControl cacheControl = new CacheControl.Builder()
//                    .maxStale(365, TimeUnit.DAYS)
//                    .build();
            int cacheSize = 10 * 1024 * 1024;// 10 MiB
            Cache cache = new Cache(App.CacheDirectory, cacheSize);

            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

                Request request = chain.request();
                if (!App.netWorkUtils.isNetworkConnected()) {
                    Log.e(TAG, "createApi: 没有网络创建带cache的request！！");
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                /**
                 * Only accept the response if it is in the cache. If the response isn't cached, a {@code 504
                 * Unsatisfiable Request} response will be returned.
                 */
                if (!App.netWorkUtils.isNetworkConnected()) {
                    Log.e(TAG, "createApi: 依然没有网络的response！！");
                    String cacheControl = request.cacheControl().toString();
                    Log.e(TAG, "createApi: " + cacheControl);
                     originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=66")
                            .build();
                } else {
                    Log.e(TAG, "createApi: 有网了的response！！");
                     originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=55")
                            .build();
                }
                Log.e(TAG, "createApi:headers " + originalResponse.headers());
                return originalResponse;
            };
            userApi = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(chain -> chain.proceed(RequestUtils.createJustJsonRequest(chain.request())))
                            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                            .addInterceptor(new LoggingInterceptor())
                            .cache(cache)
                            .build())
                    .build();


            return userApi.create(clazz);
        }
        return userApi.create(clazz);
    }
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
