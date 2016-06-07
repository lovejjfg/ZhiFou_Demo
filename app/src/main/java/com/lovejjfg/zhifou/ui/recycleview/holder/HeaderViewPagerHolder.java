package com.lovejjfg.zhifou.ui.recycleview.holder;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lovejjfg.circlepoint.SlidingCircleLayout;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.widget.HeaderImageView;
import com.lovejjfg.zhifou.ui.widget.TopViewPager;
import com.lovejjfg.zhifou.util.JumpUtils;
import com.lovejjfg.zhifou.util.ListUtils;

import java.security.PublicKey;
import java.util.List;


/**
 * Created by Aspsine on 2015/3/11.
 */
public class HeaderViewPagerHolder extends RecyclerView.ViewHolder {
    private static final String TAG = HeaderViewPagerHolder.class.getSimpleName();
    public TopViewPager viewPager;
    private SlidingCircleLayout indicator;
    private PagerAdapter mPagerAdapter;

    public HeaderViewPagerHolder(@Nullable View itemView, List<Story> stories) {
        super(itemView);

         viewPager = (TopViewPager) itemView.findViewById(R.id.viewPager);

        indicator = (SlidingCircleLayout) itemView.findViewById(R.id.scl);
        if (ListUtils.isEmpty(stories)) {
            return;
        } else if (stories.size() < 2) {
            indicator.setVisibility(View.GONE);
        }
        mPagerAdapter = new HeaderPagerAdapter(stories);
    }

    public void bindHeaderView() {
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(mPagerAdapter);
            indicator.addViewPager(viewPager);
        } else {
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    public boolean isAutoScrolling() {
        if (viewPager != null) {
            return viewPager.isAutoScrolling();
        }
        return false;
    }

    public void stopAutoScroll() {
        if (viewPager != null) {
            viewPager.stopAutoScroll();
        }
    }

    public void startAutoScroll() {
        if (viewPager != null) {
            viewPager.startAutoScroll();
        }
    }

    private final static class HeaderPagerAdapter extends PagerAdapter {
        private List<Story> mStories;

        private int mChildCount;

        public HeaderPagerAdapter(List<Story> stories) {
            mStories = stories;
        }

        @Override
        public int getCount() {
            return mStories == null ? 0 : mStories.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final HeaderImageView iv = new HeaderImageView(container.getContext());
            final Story story = mStories.get(position);
            // TODO: 2016-03-15 header的点击处理
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpUtils.jumpToDetail(((Activity) container.getContext()), iv, Integer.valueOf(story.getId()));
                }
            });
            Glide.with(container.getContext()).load(story.getImage()).into(iv.mImageView);
            iv.mTvTittle.setText(story.getTitle());
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            mChildCount = getCount();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }
}
