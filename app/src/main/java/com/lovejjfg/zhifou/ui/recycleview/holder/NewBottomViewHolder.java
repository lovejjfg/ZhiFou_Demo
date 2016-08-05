package com.lovejjfg.zhifou.ui.recycleview.holder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.ui.recycleview.AdapterLoader;
import com.lovejjfg.zhifou.ui.recycleview.SwipRefreshRecycleView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangjun on 2016-03-11.
 */
public class NewBottomViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.footer_container)
    public LinearLayout contaier;

    @Bind(R.id.progressbar)
    ProgressBar pb;
    @Bind(R.id.content)
    TextView content;
    @Nullable
    private final SwipRefreshRecycleView.OnRefreshLoadMoreListener mListener;

    public NewBottomViewHolder(View itemView, SwipRefreshRecycleView.OnRefreshLoadMoreListener listener) {

        super(itemView);
        ButterKnife.bind(this,itemView);
        mListener = listener;
    }

    public void bindDateView(int state) {
        switch (state) {
            case AdapterLoader.STATE_LASTED:
                contaier.setVisibility(View.VISIBLE);
                contaier.setOnClickListener(null);
                pb.setVisibility(View.GONE);
                content.setText("---  没有更多了  ---");
                break;
            case AdapterLoader.STATE_LOADING:
                contaier.setVisibility(View.VISIBLE);
                content.setText("加载更多！！");
                contaier.setOnClickListener(null);
                pb.setVisibility(View.VISIBLE);
                break;
            case AdapterLoader.STATE_ERROR:
                contaier.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                content.setText("--- 加载更多失败点击重试 ---");
                contaier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onLoadMore();
                        }
                        content.setText("加载更多！！");
                        pb.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }
    }

}
