package com.lovejjfg.zhifou_demo.data.api;


import com.lovejjfg.zhifou_demo.data.model.DailyStories;
import com.lovejjfg.zhifou_demo.data.model.StartImage;
import com.lovejjfg.zhifou_demo.data.model.Story;
import com.lovejjfg.zhifou_demo.data.model.Theme;
import com.lovejjfg.zhifou_demo.data.model.Themes;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Aspsine on 2015/3/30.
 */
public interface DailyApiService {

    @GET("/start-image/{width}*{height}")
    void getStartImage(@Path("width") int width, @Path("height") int height, Callback<StartImage> callback);

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
