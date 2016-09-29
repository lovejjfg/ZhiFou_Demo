package com.lovejjfg.zhifou.ui.recycleview.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.util.DateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Joe on 2015/3/11.
 * Email lovejjfg@gmail.com
 */
public class DateViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.date)
    public TextView tvDate;

    public DateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindDateView(String date) {
        tvDate.setText(getDate(date, itemView.getContext()));
    }

    public static String getDate(String date, Context context) {
        if (DateUtils.isToday(date)) {
            return context.getResources().getString(R.string.recycler_item_main_today_hottest);
        } else {
            return DateUtils.getMainPageDate(date);
        }
    }

}
