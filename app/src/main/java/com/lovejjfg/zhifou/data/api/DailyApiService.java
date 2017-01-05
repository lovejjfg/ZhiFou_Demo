package com.lovejjfg.zhifou.data.api;


import com.lovejjfg.zhifou.data.model.Result;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.StartImage;
import com.lovejjfg.zhifou.data.model.Story;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 */
public interface DailyApiService {

    @GET("start-image/{width}*{height}")
    Call<StartImage> getStartImage(@Path("width") int width, @Path("height") int height);

    @GET("news/latest")
    Observable<DailyStories> getLatestDailyStories();

    @GET("news/before/{date}")
    Observable<DailyStories> getBeforeDailyStories(@Path("date") String date);

    @GET("news/{storyId}")
    Observable<Story> getStoryDetail(@Path("storyId") String storyId);

    @GET()
    Observable<Result<DailyStories>> getList(@Url String url);



}
