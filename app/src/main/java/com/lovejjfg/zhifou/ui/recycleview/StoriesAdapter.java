package com.lovejjfg.zhifou.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.holder.BottomViewHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.DateViewHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.HeaderViewPagerHolder;
import com.lovejjfg.zhifou.ui.recycleview.holder.StoryViewHolder;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lovejjfg on 2016/2/21.
 */
public class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = StoriesAdapter.class.getSimpleName();
    protected List<Item> mItems;
    protected List<Item> mTmpItem;
    private OnItemClickListener listener;
    private boolean isLoading;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void isLoadingMore(boolean loading) {
        isLoading = loading;
    }

    public class Type {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_DATE = 1;
        public static final int TYPE_STORY = 2;
        public static final int TYPE_BOTTOM = 3;
    }

    public StoriesAdapter() {
        mItems = new ArrayList<>();
        mTmpItem = new ArrayList<>();
    }

    public void setList(DailyStories dailyStories) {
        mItems.clear();
        appendList(dailyStories);
    }

    public void appendList(DailyStories dailyStories) {
        int positionStart = mItems.size();

        if (positionStart == 0 && null != dailyStories.getTopStories()) {
            Item headerItem = new Item();
            headerItem.setType(Type.TYPE_HEADER);
            headerItem.setStories(dailyStories.getTopStories());
            mItems.add(headerItem);
        }
        Item dateItem = new Item();
        dateItem.setType(Type.TYPE_DATE);
        dateItem.setDate(dailyStories.getDate());
        mItems.add(dateItem);
        List<Story> stories = dailyStories.getStories();
        for (int i = 0, num = stories.size(); i < num; i++) {
            Item storyItem = new Item();
            storyItem.setType(Type.TYPE_STORY);
            storyItem.setStory(stories.get(i));
            mItems.add(storyItem);
        }

        int itemCount = mItems.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(positionStart, itemCount + 1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Type.TYPE_HEADER:
                itemView = UIUtils.inflate(R.layout.recycler_header_viewpager, parent);
                return new HeaderViewPagerHolder(itemView, mItems.get(0).getStories());
            case Type.TYPE_DATE:
                itemView = UIUtils.inflate(R.layout.recycler_item_date, parent);
                return new DateViewHolder(itemView);
            case Type.TYPE_STORY:
                itemView = UIUtils.inflate(R.layout.recycler_item_story, parent);
                return new StoryViewHolder(itemView);
            case Type.TYPE_BOTTOM:
                itemView = UIUtils.inflate(R.layout.recycler_footer, parent);
                return new BottomViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Item item = null;
        if (null != mItems && mItems.size() > 0 && position < mItems.size()) {
            item = mItems.get(position);
        }
        switch (viewType) {
            case Type.TYPE_HEADER:
                ((HeaderViewPagerHolder) holder).bindHeaderView();
                break;
            case Type.TYPE_DATE:
                ((DateViewHolder) holder).bindDateView(item.getDate());
                break;
            case Type.TYPE_STORY:
                ((StoryViewHolder) holder).bindStoryView(item.getStory());
                final Item finalItem = item;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != listener) {

                            listener.onItemClick(holder.itemView,((StoryViewHolder) holder).getImage(),Integer.valueOf(finalItem.getStory().getId()));
                        }
                    }
                });
                break;
            case Type.TYPE_BOTTOM:
                ((BottomViewHolder) holder).bindDateView(isLoading);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mItems.size()
                && mItems.size() > 0) {
            return mItems.get(position).getType();
        } else {
            return Type.TYPE_BOTTOM;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public String getTitleBeforePosition(int position) {
        mTmpItem.clear();
        //subList [0 , position)
        mTmpItem.addAll(mItems.subList(0, position + 1));
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
        mTmpItem.addAll(mItems.subList(0, position + 1));
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
