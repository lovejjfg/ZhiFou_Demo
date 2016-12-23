package com.lovejjfg.zhifou.view;

import android.content.Context;

import com.lovejjfg.zhifou.base.ActivityScope;
import com.lovejjfg.zhifou.base.AppComponent;
import com.lovejjfg.zhifou.presenters.ListPresenter;
import com.lovejjfg.zhifou.presenters.ListStoryPresenterModule;

import dagger.Component;
import dagger.Subcomponent;

@Subcomponent(modules = SubModule.class)
public interface SubComponent {
    Context getContext();

    String getString();

    void inject(Context context);
}
