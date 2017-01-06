package com.lovejjfg.zhifou.base;

import android.app.Application;
import android.content.Context;

import com.lovejjfg.zhifou.data.api.DailyApiService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {

    Context context();  // 提供Applicaiton的Context

    DailyApiService getdailyApiService();  // 所有Api请求的管理类

    @Named("number")
    int getNumber();

    @Named("age")
    int getAge();//名字不重要，返回类型才重要，不能有重复的类型。

    void inject(Application app);

}
