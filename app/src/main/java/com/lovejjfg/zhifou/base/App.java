package com.lovejjfg.zhifou.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Joe on 2016-04-05
 * Email: zhangjun166@pingan.com.cn
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
