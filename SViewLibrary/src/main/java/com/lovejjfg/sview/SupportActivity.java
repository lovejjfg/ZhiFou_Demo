package com.lovejjfg.sview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lovejjfg.sview.utils.FragmentsUtil;
import com.lovejjfg.sview.utils.KeyBoardUtil;
import com.lovejjfg.sview.utils.ToastUtil;


/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

public abstract class SupportActivity extends AppCompatActivity implements ISupportFragment,ISupportView {

    public FragmentsUtil fragmentsUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentsUtil = new FragmentsUtil(getSupportFragmentManager());
    }

    public FragmentsUtil getFragmentsUtil() {
        return fragmentsUtil;
    }

    @Override
    public void initFragments(Bundle savedInstanceState, SupportFragment fragment) {
        fragmentsUtil.initFragments(savedInstanceState, fragment);
    }

    @Nullable
    @Override
    public SupportFragment getTopFragment() {
        return fragmentsUtil.getTopFragment();
    }

    @Nullable
    @Override
    public SupportFragment findFragment(String className) {
        return fragmentsUtil.findFragment(className);
    }

    @Override
    public void loadRoot(int containerViewId, SupportFragment root) {
        fragmentsUtil.loadRoot(containerViewId, root);
    }

    @Override
    public void addToShow(SupportFragment from, SupportFragment to) {
        fragmentsUtil.addToShow(from, to);
    }

    @Override
    public boolean popTo(Class<? extends SupportFragment> target, boolean includeSelf) {
        return fragmentsUtil.popTo(target, includeSelf);
    }

    public boolean popSelf() {
        return fragmentsUtil.popSelf();
    }

    @Override
    public void replaceToShow(SupportFragment from, SupportFragment to) {
        fragmentsUtil.replaceToShow(from, to);
    }


    @Override
    public void onBackPressed() {
        if (!finishSelf()) {
            super.onBackPressed();
        }
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.showToast(this, toast);
    }

    @Override
    public void showToast(@StringRes int StringId) {
        showToast(getString(StringId));
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void closeLoadingDialog() {

    }

    @Override
    public void openKeyBoard() {
        KeyBoardUtil.openKeyBoard(this);
    }

    @Override
    public void openKeyBoard(View focusView) {
        KeyBoardUtil.openKeyBoard(this, focusView);
    }

    @Override
    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyBoard(this);
    }


    @Override
    public boolean finishSelf() {
        SupportFragment topFragment = getTopFragment();
        if (topFragment == null || !topFragment.finishSelf()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
            return true;
        }
        return true;
    }

//    @Override
//    public void saveToSharedPrefs(String key, Object value) {
//
//    }

    @Override
    public void saveViewData(Bundle bundle) {

    }

    @Override
    public void saveViewData(String key, Bundle bundle) {

    }
}
