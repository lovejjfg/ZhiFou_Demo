package com.lovejjfg.zhifou.presenters;

/**
 * Created by zhangjun on 2016-03-19.
 */
public interface SpecifiedDatePresenter extends BasePresenter {
    void onLoading(String date);

    void onItemClicked(android.view.View itemView, android.view.View image, int id);
}
