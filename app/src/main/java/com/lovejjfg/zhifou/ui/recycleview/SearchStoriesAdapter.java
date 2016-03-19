package com.lovejjfg.zhifou.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.SearchResult;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.holder.StoryViewHolder;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovejjfg on 2016/2/21.
 */
public class SearchStoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = SearchStoriesAdapter.class.getSimpleName();
    protected List<SearchResult> mItems;
    private OnItemClickListener listener;
    private boolean isLoading;
    private Story story;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void isLoadingMore(boolean loading) {
        isLoading = loading;
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public class Type {
        public static final int TYPE_STORY = 2;
    }

    public SearchStoriesAdapter() {
        mItems = new ArrayList<>();
    }

    public void setList(List<SearchResult> list) {
        mItems.clear();
        appendList(list);
    }

    public void appendList(List<SearchResult> stories) {
        mItems = stories;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView;
        itemView = UIUtils.inflate(R.layout.recycler_item_story, parent);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (null == story) {
            story = new Story();
        }
        if (null != mItems && mItems.size() > 0 && position < mItems.size()) {
            SearchResult searchResult = mItems.get(position);
            SearchResult.ContentEntity content = searchResult.getContent();

            if (content.getThumbnailUrl() != null) {
                story.setImage(content.getThumbnailUrl());
            }
            if (content.getQuestionTitle() != null) {
                story.setTitle(content.getQuestionTitle());
            }
            if (content.getDailyTitle() != null) {
                story.setBody(content.getDailyTitle());
            }
        }
        bindStoryView(((StoryViewHolder) holder), story);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
//                    listener.onItemClick(holder.itemView, ((StoryViewHolder) holder).getImage(), Integer.valueOf(finalItem.getStory().getId()));
                }
            }
        });
    }

    private void bindStoryView(StoryViewHolder holder, Story mStory) {
        if (mStory.getTheme() == null) {
            holder.theme.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }
        holder.text.setText(String.valueOf(mStory.getTitle()));

        String imageUrl = story.getImage();
        if (TextUtils.isEmpty(imageUrl)) {
            holder.image.setVisibility(View.GONE);
        } else {
            holder.image.setVisibility(View.VISIBLE);
            Glide.with( holder.image.getContext()).load(imageUrl).into( holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
