package com.lovejjfg.zhifou.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Joe on 2016/12/25.
 * Email lovejjfg@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = ((App) getApplication()).getAppComponent();
        initInject(appComponent);
    }

    public abstract void initInject(AppComponent appComponent);
}
