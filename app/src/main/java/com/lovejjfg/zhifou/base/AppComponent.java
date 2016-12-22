package com.lovejjfg.zhifou.base;

import android.content.Context;

import com.lovejjfg.zhifou.data.api.DailyApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class})
public interface AppComponent {

    Context context();  // 提供Applicaiton的Context

    DailyApiService getdailyApiService();  // 所有Api请求的管理类

    int getNumber();
}
