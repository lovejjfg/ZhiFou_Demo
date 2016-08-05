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
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        return new MyViewHolder(textView);
    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindDateView("这是" + list.get(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView;
        }

        public void bindDateView(String s) {
            mTv.setText(s);
        }
    }

    @Override
    public RecyclerView.ViewHolder onBottomViewHolderCreate(View loadMore) {
        return new NewBottomViewHolder(loadMore, null);
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
