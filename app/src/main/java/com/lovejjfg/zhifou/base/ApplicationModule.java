package com.lovejjfg.zhifou.base;

import android.content.Context;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.api.DailyApiService;

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
    int provideNumber() {
        return 5;
    }
}