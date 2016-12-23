package com.lovejjfg.zhifou.view;

import com.lovejjfg.zhifou.base.ActivityScope;
import com.lovejjfg.zhifou.base.AppComponent;
import com.lovejjfg.zhifou.presenters.ListPresenter;
import com.lovejjfg.zhifou.presenters.ListStoryPresenterModule;

import dagger.Component;
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ListStoryPresenterModule.class)
public interface ListStoryComponent {

    String getName(String name);

//    AbsPerson getPerson();

    ListPresenter.View getView();

    SubComponent getSubComponent(SubModule module);


    void inject(ListStory view);
}
