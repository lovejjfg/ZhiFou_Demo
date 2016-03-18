package com.lovejjfg.zhifou.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.view.DetailStory;

/**
 * Created by 张俊 on 2016/3/18.
 */
public class JumpUtils {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void jumpToDetail(Activity activity, View itemView, int id) {
        Intent i = new Intent(activity, DetailStory.class);
        i.putExtra(Constants.ID, id);
        final ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(itemView,
                                activity.getResources().getString(R.string.detail_view))
                );
        activity.startActivity(i, options.toBundle());
    }
}
