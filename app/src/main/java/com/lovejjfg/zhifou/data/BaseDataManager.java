package com.lovejjfg.zhifou.data;

import android.util.Log;

import com.lovejjfg.zhifou.base.App;
import com.lovejjfg.zhifou.data.api.DailyApiService;
import com.lovejjfg.zhifou.data.model.Result;
import com.lovejjfg.zhifou.util.CacheControlInterceptor;
import com.lovejjfg.zhifou.util.LoggingInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    private static <T> T createApi(Class<T> clazz) {
        if (userApi == null) {
            int cacheSize = 10 * 1024 * 1024;// 10 MiB
            Cache cache = new Cache(App.CacheDirectory, cacheSize);
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            // TODO: 2017/1/5 cookie是显示在header里面的吗
            CookieJar cookieJar = new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    Cookie.Builder b =
                            new Cookie.Builder()
                                    .domain("zhihu.com")
                                    .path("/")
                                    .name("testName")
                                    .value("saveFromResponse")
                                    .httpOnly()
                                    .secure();
                    cookies.add(b.build());
                    cookieStore.put(url.host(), cookies);
                    Log.e(TAG, "saveFromResponse: " + cookies.toString());
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    if (cookies == null) {
                        cookies = new ArrayList<>();
                    }
                    Cookie.Builder b =
                            new Cookie.Builder()
                                    .domain("zhihu.com")
                                    .path("/")
                                    .name("testName")
                                    .value("loadForRequest");
//                            .httpOnly()
//                            .secure()
                    cookies.add(b.build());
                    Log.e(TAG, "loadForRequest: " + cookies.toString());
                    return cookies;
                }
            };

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e(TAG, "log: " + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            // TODO: 2017/1/4 缓存不刷新数据的节奏？
            userApi = new Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
//                            .cookieJar(cookieJar)
                            .cache(cache)
                            .addInterceptor(chain -> chain.proceed(RequestUtils.createJustJsonRequest(chain.request())))
                            .addInterceptor(new CacheControlInterceptor())
                            .addInterceptor(new LoggingInterceptor())
//                            .addInterceptor(loggingInterceptor)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build())
                    .build();


            return userApi.create(clazz);
        }
        return userApi.create(clazz);
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


    public static <R> Subscription handleService(Observable<Result<R>> observable, Action1<R> callSuccess) {
        return observable
                .subscribeOn(Schedulers.io())//事件产生在子线程
                .map(result -> {
                    R data = result.getData();
                    int code = result.getCode();
                    if (code != 200) {
                        new BaseException(code, "发生错误了！！");
                    }
                    return data;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callSuccess);
    }

    public static <R> Subscription handleService(Observable<Result<R>> observable, Action1<R> callSuccess, Action1<Throwable> callError) {
        return observable
                .subscribeOn(Schedulers.io())//事件产生在子线程
                .map(result -> {
                    R data = result.getData();
                    int code = result.getCode();
                    if (code != 200) {
                        new BaseException(code, "发生错误了！！");
                    }
                    return data;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callSuccess, callError);
    }

    public static <R> Subscription handleService(Observable<Result<R>> observable, Action1<R> callSuccess, Action1<Throwable> callError, Action0 doOnSubscribe) {
        return observable
                .subscribeOn(Schedulers.io())//事件产生在子线程
                .map(result -> {
                    R data = result.getData();
                    int code = result.getCode();
                    if (code != 200) {
                        throw new BaseException(code, "发生错误了！！");
                    }
                    return data;
                })
                .doOnSubscribe(doOnSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())//事件产生在子线程
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(callSuccess, callError);
    }

    public static <R> Subscription handleNormalService(Observable<R> observable, Action1<R> callSuccess, Action1<Throwable> callError, Action0 doOnSubscribe) {
        return observable
                .subscribeOn(Schedulers.io())//事件产生在子线程
                .doOnSubscribe(doOnSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())//doOnSubscribe产生在主线程
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(callSuccess, callError);
    }
}
