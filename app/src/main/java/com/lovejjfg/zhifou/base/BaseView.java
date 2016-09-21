package com.lovejjfg.zhifou.base;

/**
 * Created by Joe on 2016-03-10.
 * There are some base methods for View in MVP.
 * You should make your ViewInterface Extend this..
 */
@Deprecated
public interface BaseView {
    void showLoading();
    void closeLoading();
    void makeToast(String msg);
}
