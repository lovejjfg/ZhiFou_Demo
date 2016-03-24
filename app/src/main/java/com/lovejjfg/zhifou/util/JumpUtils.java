package com.lovejjfg.zhifou.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.view.DatePick;
import com.lovejjfg.zhifou.view.DetailStory;
import com.lovejjfg.zhifou.view.SpecifiedDateStory;

/**
 * Created by 张俊 on 2016/3/18.
 */
public class JumpUtils {
    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void jumpToDetail(Activity activity, View itemView, int id) {
        Intent i = new Intent(activity, DetailStory.class);
        i.putExtra(Constants.ID, id);
        final ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(itemView, activity.getString(R.string
                                .detail_container))
                        );
        activity.startActivity(i, options.toBundle());
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void jumpToDataPicker(Activity activity, View view) {
        Intent i = new Intent(activity, DatePick.class);
        i.putExtra(Constants.FIRST, true);
        final ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(view,
                                activity.getString(R.string.date_picker)));
        activity.startActivity(i, options.toBundle());
    }

    public static void jumpToSpecifiedDate(Activity activity, String date) {
        Intent i = new Intent(activity, SpecifiedDateStory.class);
        i.putExtra(Constants.DATE, date);
        activity.startActivity(i);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void jumpToDataPickerForResult(Activity activity, View view, int requestCode) {
        Intent i = new Intent(activity, DatePick.class);
        i.putExtra(Constants.FIRST, false);
        final ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(activity,
                        Pair.create(view,
                                activity.getString(R.string.date_picker)));
        activity.startActivityForResult(i, requestCode, options.toBundle());
    }
}
