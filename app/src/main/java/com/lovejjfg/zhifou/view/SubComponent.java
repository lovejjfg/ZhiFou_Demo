package com.lovejjfg.zhifou.view;

import com.lovejjfg.zhifou.base.SubScope;

import dagger.Subcomponent;
@SubScope
@Subcomponent(modules = SubModule.class)
public interface SubComponent {

//    String getString();

    void inject(ListStory context);

    void inject(DatePick context);
}
