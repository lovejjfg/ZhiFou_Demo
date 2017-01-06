package com.lovejjfg.zhifou.view;

import android.content.Context;

import com.lovejjfg.zhifou.base.ActivityScope;
import com.lovejjfg.zhifou.base.SubScope;
import com.lovejjfg.zhifou.presenters.ListPresenter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SubModule {

    private final Context mView;

    public SubModule(Context view) {
        mView = view;
    }


    @Provides
    @Named("sub")
    String provideSubString() {
        return "sub";
    }


}
