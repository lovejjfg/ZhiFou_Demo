package com.lovejjfg.zhifou.view;

import com.lovejjfg.zhifou.data.model.DailyStories;

/**
 * Created by zhangjun on 2016-03-19.
 */
public interface SpecifiedDateView extends LoadingView {

    void onLoadMore(DailyStories stories);

    void onLoadError(String errorCode);
}
