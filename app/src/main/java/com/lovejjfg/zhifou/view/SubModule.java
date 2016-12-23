package com.lovejjfg.zhifou.view;

import android.content.Context;

import com.lovejjfg.zhifou.presenters.ListPresenter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class SubModule {

    private final Context mView;

    public SubModule(Context view) {
        mView = view;
    }


    @Provides
    @Named("sub")
    String provideString() {
        return "sub";
    }


}
