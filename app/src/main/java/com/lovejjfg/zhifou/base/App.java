package com.lovejjfg.zhifou.base;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Joe on 2016-04-05
 * Email: lovejjfg@gmail.com
 */
public class App extends Application {
    AppComponent mAppComponent;

    @Inject
    @Named("age")
    int age;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
        mAppComponent.inject(this);
        Log.e("TAG", "AppOnCreate: " + age);

        LeakCanary.install(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
