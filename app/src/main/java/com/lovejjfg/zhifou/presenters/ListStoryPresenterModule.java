package com.lovejjfg.zhifou.presenters;

import com.lovejjfg.zhifou.data.AbsPerson;
import com.lovejjfg.zhifou.data.Person;

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

    @Provides
    String provideString() {
        return "Test1111";
    }

    @Provides
    AbsPerson providePerson(Person person) {
        return person;
    }


}
