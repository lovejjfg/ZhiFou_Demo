package com.lovejjfg.zhifou.ui.recycleview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.powerrecycle.RefreshRecycleAdapter;
import com.lovejjfg.powerrecycle.SwipeRefreshRecycleView;
import com.lovejjfg.powerrecycle.holder.NewBottomViewHolder;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.holder.DateViewHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.HeaderViewPagerHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.StoryViewHolder;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joe on 2016/2/21.
 * Email lovejjfg@gmail.com
 */
public class StoriesRecycleAdapter extends RefreshRecycleAdapter<StoriesRecycleAdapter.Item> {
    public static final String TAG = StoriesRecycleAdapter.class.getSimpleName();
//    protected List<Item> list;
    @NonNull
    protected List<Item> mTmpItem;
    @Nullable
    private OnItemClickListener listener;
    public HeaderViewPagerHolder headerViewPagerHolder;
    private  SwipeRefreshRecycleView.OnRefreshLoadMoreListener mListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class Type {
        public static final int TYPE_HEADER = 3;
        public static final int TYPE_DATE = 1;
        public static final int TYPE_STORY = 2;
    }

    public StoriesRecycleAdapter(SwipeRefreshRecycleView.OnRefreshLoadMoreListener listener) {
        mTmpItem = new ArrayList<>();
        mListener = listener;
    }

    public void setList(DailyStories dailyStories) {
        list.clear();
        appendList(dailyStories);
    }

    public void appendList(DailyStories dailyStories) {
        int positionStart = list.size();

        if (positionStart == 0 && null != dailyStories.getTopStories()) {
            Item headerItem = new Item();
            headerItem.setType(Type.TYPE_HEADER);
            headerItem.setStories(dailyStories.getTopStories());
            list.add(headerItem);
        }
        Item dateItem = new Item();
        dateItem.setType(Type.TYPE_DATE);
        dateItem.setDate(dailyStories.getDate());
        list.add(dateItem);
        List<Story> stories = dailyStories.getStories();
        for (int i = 0, num = stories.size(); i < num; i++) {
            Item storyItem = new Item();
            storyItem.setType(Type.TYPE_STORY);
            storyItem.setStory(stories.get(i));
            list.add(storyItem);
        }

        int itemCount = list.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(positionStart, itemCount + 1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Type.TYPE_HEADER:
                itemView = UIUtils.inflate(R.layout.recycler_header_viewpager, parent);
                headerViewPagerHolder = new HeaderViewPagerHolder(itemView, list.get(0).getStories());
                return headerViewPagerHolder;
            case Type.TYPE_DATE:
                itemView = UIUtils.inflate(R.layout.recycler_item_date, parent);
                return new DateViewHolder(itemView);
            case Type.TYPE_STORY:
                itemView = UIUtils.inflate(R.layout.recycler_item_story, parent);
                return new StoryViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onViewHolderBind(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewTypes(position);
        Item item;
        if (list.size() == 0 || position >= list.size() - 1) {
            return;
        }
        item = list.get(position);
        switch (viewType) {
            case Type.TYPE_HEADER:
                ((HeaderViewPagerHolder) holder).bindHeaderView();
                break;
            case Type.TYPE_DATE:
                if (item != null) {
                    ((DateViewHolder) holder).bindDateView(item.getDate());
                }
                break;
            case Type.TYPE_STORY:
                assert item != null;
                ((StoryViewHolder) holder).bindStoryView(item.getStory());
                final Item finalItem = item;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != listener) {
                            listener.onItemClick(holder.itemView, ((StoryViewHolder) holder).getImage(), Integer.valueOf(finalItem.getStory().getId()));
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewTypes(int position) {
        if (position < list.size()
                && list.size() > 0) {
            return list.get(position).getType();
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onBottomViewHolderCreate(View loadMore) {
        return new NewBottomViewHolder(loadMore,mListener);
    }


//    @Override
//    public void appendList(Item item) {
//
//    }

    @Override
    public void onBottomViewHolderBind(RecyclerView.ViewHolder holder, int state) {
        ((NewBottomViewHolder) holder).bindDateView(state);
    }


    public Item getItem(int position) {
        return list.get(position);
    }

    public String getTitleBeforePosition(int position) {
        mTmpItem.clear();
        //subList [0 , position)
        mTmpItem.addAll(list.subList(0, position + 1));
        Collections.reverse(mTmpItem);
        for (Item item : mTmpItem) {
            if (item.getType() == Type.TYPE_DATE) {
                return item.getDate();
            }
        }
        //L.i(TAG, "POSITION = " + position);
        return "";
    }

    // TODO: 2016-03-01 这个方法需要重新写
    public String getTitleAtPosition(int position) {
        mTmpItem.clear();
        //subList [0 , position)
        mTmpItem.addAll(list.subList(0, position + 1));
        Collections.reverse(mTmpItem);
        for (Item item : mTmpItem) {
            if (item.getType() == Type.TYPE_DATE) {
                return item.getDate();
            }
        }
        //L.i(TAG, "POSITION = " + position);
        return null;
    }

    public static class Item {
        private int type;
        private String date;
        private Story story;
        private List<Story> stories;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Story getStory() {
            return story;
        }

        public void setStory(Story story) {
            this.story = story;
        }

        public List<Story> getStories() {
            return stories;
        }

        public void setStories(List<Story> stories) {
            this.stories = stories;
        }
    }
}
