package com.lovejjfg.zhifou.view;

import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.SearchResult;

import java.util.List;

/**
 * Created by zhangjun on 2016-03-19.
 */
public interface SearchView {
    void onSearchOut(List<SearchResult> list);

    void onSearchEmpt();
}
