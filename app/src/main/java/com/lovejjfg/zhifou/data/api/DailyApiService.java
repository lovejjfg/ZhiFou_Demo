package com.lovejjfg.zhifou.data.api;


import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.StartImage;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.data.model.Theme;
import com.lovejjfg.zhifou.data.model.Themes;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 */
public interface DailyApiService {

    @GET("/start-image/{width}*{height}")
    void getStartImage(@Path("width") int width, @Path("height") int height, Callback<StartImage> callback);

    @GET("/news/latest")
    void getLatestDailyStories(Callback<DailyStories> callback);
    @GET("/news/latest")
    Observable<DailyStories> getLatestDailyStories();

    @GET("/news/before/{date}")
    void getBeforeDailyStories(@Path("date") String date, Callback<DailyStories> callback);

    @GET("/news/{storyId}")
    void getStoryDetail(@Path("storyId") String storyId, Callback<Story> callback);

    @GET("/themes")
    void getThemes(Callback<Themes> callback);

    @GET("/theme/{themeId}")
    void getTheme(@Path("themeId") String themeId, Callback<Theme> callback);

    @GET("/theme/{themeId}/before/{storyId}")
    void getThemeBeforeStory(@Path("themeId") String themeId, @Path("storyId") String storyId, Callback<Theme> callback);
}
