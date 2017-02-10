package com.lovejjfg.zhifou.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.api.DailyApiService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mContext;
    }

    @Singleton
    @Provides
    DailyApiService provideDailyApiService() {
        return BaseDataManager.getDailyApiService();
    }

    @Singleton
    @Provides
    @Named("number")
    int provideNumber() {
        return 5;
    }

    @Provides
    @Named("age")
    int provideAge() {
        return 500;
    }


}