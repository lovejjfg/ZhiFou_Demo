package com.lovejjfg.zhifou.ui.recycleview.holder;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.util.glide.CircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Aspsine on 2015/3/11.
 */
public class StoryViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.text)
    public TextView text;
    @Bind(R.id.theme)
    public TextView theme;
    private boolean isUpdateByHand;

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.ivMultiPic)
    public ImageView ivMultiPic;
    @Bind(R.id.line)
    public ImageView line;
    public Story mStory;

    public StoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bindStoryView(Story story) {
        mStory = story;
        if (mStory.getTheme() == null) {
            theme.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            theme.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append(itemView.getResources().getString(R.string.recycler_item_main_select_from))
                    .append(" ").append("<font color=\"#2196F3\">")
                    .append(mStory.getTheme().getName())
                    .append("</font>");
            theme.setText(Html.fromHtml(sb.toString()));
        }
        text.setText(String.valueOf(mStory.getTitle()));

        String imageUrl = mStory.getImages() == null ? "" : mStory.getImages().get(0);
        if (TextUtils.isEmpty(imageUrl) || TextUtils.isEmpty(mStory.getMultiPic())) {
            ivMultiPic.setVisibility(View.GONE);
        } else if (Boolean.valueOf(mStory.getMultiPic())) {
            ivMultiPic.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(imageUrl)) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            Glide.with(image.getContext())
                    .load(imageUrl)
//                    .skipMemoryCache(isUpdateByHand)
                    .error(R.mipmap.girl)
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade(R.anim.fade_out_rapidly, 5000)
                    .transform(new CircleTransform(image.getContext()))
                    .into(image);
        }
    }
}
