package com.lovejjfg.zhifou.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.util.UIUtils;

/**
 * Created by zhangjun on 2016-02-27.
 */
public class HeaderImageView extends FrameLayout {

    public ImageView mImageView;
    public TextView mTvTittle;

    public HeaderImageView(Context context) {
        this(context, null);
    }

    public HeaderImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = UIUtils.inflate(R.layout.recycler_header, this);
        mImageView = (ImageView) inflate.findViewById(R.id.iv_header);
        mTvTittle = (TextView) inflate.findViewById(R.id.tv_tittle);
        addView(inflate);

    }

}
