package com.lovejjfg.zhifou.ui.recycleview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.zhifou.R;

/**
 * Created by zhangjun on 2016-03-11.
 */
public class BottomViewHolder extends RecyclerView.ViewHolder{
    public ProgressBar pb;

    public BottomViewHolder(View itemView) {
        super(itemView);

        pb = (ProgressBar) itemView.findViewById(R.id.progressbar);
    }

    public void bindDateView(int state) {
        pb.setVisibility(AdapterLoader.STATE_LOADING == state ? View.VISIBLE : View.GONE);
    }

}
