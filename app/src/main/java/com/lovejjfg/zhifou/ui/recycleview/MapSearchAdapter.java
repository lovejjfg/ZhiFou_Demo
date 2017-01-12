package com.lovejjfg.zhifou.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.holder.StoryViewHolder;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lovejjfg on 2016/2/21.
 */
public class MapSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = MapSearchAdapter.class.getSimpleName();
    public List<PoiInfo> mItems;
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

    public MapSearchAdapter() {
        mItems = new ArrayList<>();
    }

    public void setList(List<PoiInfo> list) {
        mItems.clear();
        appendList(list);
    }

    public void appendList(List<PoiInfo> stories) {
        mItems = stories;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView;
        itemView = UIUtils.inflate(R.layout.recycler_item_map, parent);
        return new MapViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (null != mItems && mItems.size() > 0 && position < mItems.size()) {
            PoiInfo searchResult = mItems.get(position);
            bindStoryView(((MapViewHolder) holder), searchResult);
        }
        holder.itemView.setOnClickListener(v -> {
            if (null != listener) {
                listener.onItemClick(v,null,holder.getAdapterPosition());
            }
        });
    }

    private void bindStoryView(MapViewHolder holder, PoiInfo mStory) {
        holder.mText.setText(mStory.name);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static class MapViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        public TextView mText;

        public MapViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

