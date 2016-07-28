package com.lovejjfg.zhifou.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Joe on 2016-07-27
 * Email: lovejjfg@pingan.com
 */
public class MyRecycleAdapter extends RefreshRecycleAdapter<String> {

    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewTypes(int position) {
        return 0;
    }

}
