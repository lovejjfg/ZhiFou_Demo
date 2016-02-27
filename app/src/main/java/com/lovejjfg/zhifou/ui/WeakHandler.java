package com.lovejjfg.zhifou.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by lovejjfg on 2016/2/21.
 */
public abstract class WeakHandler extends Handler {

    private final WeakReference<Context> contextWeakReference;

    public WeakHandler(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        Activity ac = (Activity) contextWeakReference.get();
        if (ac != null && !ac.isFinishing()) {
            handleMessages(msg);
        }
    }

    public abstract void handleMessages(Message msg);
}
