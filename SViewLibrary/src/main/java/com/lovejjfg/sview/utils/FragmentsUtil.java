package com.lovejjfg.sview.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lovejjfg.sview.SupportFragment;

import java.util.List;


/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public class FragmentsUtil {
    private FragmentManager manager;

    public FragmentsUtil(FragmentManager manager) {
        this.manager = manager;
    }

    public void addToShow(SupportFragment from, SupportFragment to) {
        bindContainerId(from.getContainerId(), to);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = to.getClass().getSimpleName();
        transaction.add(from.getContainerId(), to, tag)
                .addToBackStack(tag)
                .hide(from)
                .show(to)
                .commit();

    }

    public void replaceToShow(SupportFragment from, SupportFragment to) {
        bindContainerId(from.getContainerId(), to);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = to.getClass().getSimpleName();
        transaction.replace(from.getContainerId(), to, tag)
                .addToBackStack(tag)
                .commit();

    }

    public void loadRoot(int containerViewId, SupportFragment root) {
        bindContainerId(containerViewId, root);
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = root.getClass().getSimpleName();
        transaction.add(containerViewId, root, tag)
                .addToBackStack(tag)
                .commit();


    }

    private void bindContainerId(int containerId, SupportFragment to) {
        Bundle args = to.getArguments();
        if (args == null) {
            args = new Bundle();
            to.setArguments(args);
        }
        args.putInt(SupportFragment.ARG_CONTAINER, containerId);
    }

    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        if (savedInstanceState == null) {
            return;
        }
        boolean isSupportHidden = savedInstanceState.getBoolean(SupportFragment.ARG_IS_HIDDEN);

        FragmentTransaction ft = manager.beginTransaction();
        if (isSupportHidden) {
            ft.hide(fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }


    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        int flag;
        if (includeSelf) {
            flag = FragmentManager.POP_BACK_STACK_INCLUSIVE;
        } else {
            flag = 0;
        }
        return manager.popBackStackImmediate(target.getSimpleName(), flag);
    }

    public boolean popSelf() {
        return manager.popBackStackImmediate();
    }

    @Nullable
    public SupportFragment findFragment(@NonNull String className) {
        Fragment tagFragment = manager.findFragmentByTag(className);
        if (tagFragment instanceof SupportFragment) {
            return (SupportFragment) tagFragment;
        }
        return null;
    }

    @Nullable
    public SupportFragment getTopFragment() {
        List<Fragment> fragments = manager.getFragments();
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            if (f instanceof SupportFragment) {
                return (SupportFragment) f;
            }
        }
        return null;
    }
}
