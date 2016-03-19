package com.lovejjfg.zhifou.view;

import com.lovejjfg.zhifou.data.model.DailyStories;

/**
 * Created by zhangjun on 2016-03-19.
 */
public interface SearchView {
    void onSearchOut(DailyStories stories);

    void onSearchEmpt();
}
