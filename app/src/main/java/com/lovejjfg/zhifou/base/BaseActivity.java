package com.lovejjfg.zhifou.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.lovejjfg.sview.SupportActivity;
import com.lovejjfg.zhifou.presenters.BasePresenter;
import com.lovejjfg.zhifou.view.IBaseView;

/**
 * Created by Joe on 2016/12/28.
 * Email lovejjfg@gmail.com
 */

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements IBaseView<P> {
    P presenter = setPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
