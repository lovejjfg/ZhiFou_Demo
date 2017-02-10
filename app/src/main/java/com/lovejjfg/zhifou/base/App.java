package com.lovejjfg.zhifou.base;

import android.app.Application;
import android.util.Log;

import com.antfortune.freeline.FreelineCore;
import com.lovejjfg.zhifou.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.http.HEAD;

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
        FreelineCore.init(this);
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "0c34744d13", true);

        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
        Log.e("TAG", "onCreate: " + name);
        Log.e("TAG", "onCreate:是否可以调试： " + BuildConfig.IS_DEBUG);
        LeakCanary.install(this);
        // Normal app init code...


    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
