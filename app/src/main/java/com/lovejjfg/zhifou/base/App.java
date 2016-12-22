package com.lovejjfg.zhifou.base;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

/**
 * Created by Joe on 2016-04-05
 * Email: lovejjfg@gmail.com
 */
public class App extends Application {
    AppComponent mAppComponent;
    @Inject
    int name;
    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
        Log.e("TAG", "onCreate: " + name);

        LeakCanary.install(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
