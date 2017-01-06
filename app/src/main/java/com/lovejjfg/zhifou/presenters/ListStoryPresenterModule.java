package com.lovejjfg.zhifou.presenters;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

    //    @Provides
//    Lazy<String> provideLazyString() {
//
//        return () -> "lazy1111";
//    }
    @Provides
    @Named("name")
    String provideString() {
        return "Joe";
    }
    @Provides
    @Named("birth")
    String provideBirthString() {
        return "19921025";
    }



}
