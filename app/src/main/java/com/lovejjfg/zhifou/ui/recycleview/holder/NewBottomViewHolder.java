package com.lovejjfg.zhifou.ui.recycleview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.ui.recycleview.AdapterLoader;

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

    public NewBottomViewHolder(View itemView) {

        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindDateView(int state) {
        switch (state) {
            case AdapterLoader.STATE_LASTED:
                contaier.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                content.setText("没有更多了！");
                break;
            case AdapterLoader.STATE_DISMISS:
                contaier.setVisibility(View.GONE);
                break;
            case AdapterLoader.STATE_LOADING:
                contaier.setVisibility(View.VISIBLE);
                break;
        }
    }

}
