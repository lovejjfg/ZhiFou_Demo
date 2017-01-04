package com.lovejjfg.sview;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public interface ISupportFragment extends ISupportView {
    void initFragments(Bundle savedInstanceState, SupportFragment fragment);

    @Nullable
    SupportFragment getTopFragment();

    @Nullable
    SupportFragment findFragment(String className);

    void loadRoot(int containerViewId, SupportFragment root);

    void addToShow(SupportFragment from, SupportFragment to);

    boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf);

    void replaceToShow(SupportFragment from, SupportFragment to);

    void saveViewData(Bundle bundle);

    void saveViewData(String key, Bundle bundle);

}
