package com.lovejjfg.zhifou.presenters;

import javax.inject.Singleton;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.StringKey;

@Singleton
@Module
public class ListStoryPresenterModule {

    private final ListPresenter.View mView;

    public ListStoryPresenterModule(ListPresenter.View view) {
        mView = view;
    }

    @Provides
    ListPresenter.View provideView() {
        return mView;
    }

    @Provides
    String provideString() {
        return "Test1111";
    }


}
