package com.lovejjfg.zhifou.util;

import android.util.SparseArray;

import com.lovejjfg.zhifou.data.BaseException;
import com.lovejjfg.zhifou.view.IBaseView;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Joe on 2017/1/5.
 * Email lovejjfg@gmail.com
 */

public class ErrorUtil {

    private static SparseArray<String> map;

    static {
        map = new SparseArray<>();
        map.put(504, "无网络,无缓存！");
    }


    public static void handleError(IBaseView view, Throwable throwable, boolean showToast, boolean showDialog) {
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            if (code == 504) {
                if (showToast) {
                    view.showToast(map.get(code));
                }
            }
            return;
        }
        if (throwable instanceof BaseException) {
            if (showToast) {
                view.showToast(((BaseException) throwable).message());
            }
        }

    }
}
