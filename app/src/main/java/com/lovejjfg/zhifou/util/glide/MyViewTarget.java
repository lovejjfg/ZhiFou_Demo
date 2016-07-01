package com.lovejjfg.zhifou.util.glide;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by Joe on 2016-06-06
 * Email: lovejjfg@163.com
 */
public class MyViewTarget extends ViewTarget<MyFrameLayout, GlideDrawable> {
    public MyViewTarget(MyFrameLayout view) {
        super(view);

    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
        this.view.setImage(resource.getCurrent());

    }
}
