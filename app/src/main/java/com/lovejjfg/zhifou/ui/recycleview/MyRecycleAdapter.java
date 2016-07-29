package com.lovejjfg.zhifou.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovejjfg.zhifou.ui.recycleview.holder.NewBottomViewHolder;

/**
 * Created by Joe on 2016-07-27
 * Email: lovejjfg@pingan.com
 */
public class MyRecycleAdapter extends RefreshRecycleAdapter<String> {

    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        return new MyViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindDateView("这是" + position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView;
        }

        public void bindDateView(String s) {
            mTv.setText(s + "hahaahah");
        }
    }

    @Override
    public RecyclerView.ViewHolder onBottomViewHolderCreate(View loadMore) {
        return null;
    }


//    @Override
//    public void appendList(Item item) {
//
//    }

    @Override
    public void onBottomViewHolderBind(RecyclerView.ViewHolder holder, int state) {
        ((NewBottomViewHolder) holder).bindDateView(state);
    }
}
