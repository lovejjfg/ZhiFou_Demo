package com.lovejjfg.zhifou.ui.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.ui.recycleview.holder.BottomViewHolder;
import com.lovejjfg.zhifou.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 2016-03-11
 * Email: lovejjfg@gmail.com
 */
public abstract class RefreshRecycleAdapter<T> extends RecyclerView.Adapter implements AdapterLoader<T> {

    private View loadMore;
    private int loadState = STATE_LOADING;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    private int totalCount;

    public RefreshRecycleAdapter() {
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public final void setList(List<T> data) {
        if (data == null) {
            return;
        }
        if (list != null) {
            list.clear();
        }
        this.list = data;
        notifyDataSetChanged();
    }

    @Override
    public final void appendList(List<T> data) {
        int positionStart = list.size();
        list.addAll(data);
        int itemCount = list.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(positionStart, itemCount + 1);
        }
    }

    public List<T> list;
    public static final int TYPE_BOTTOM = 400;

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BOTTOM:
                if (loadMore != null) {
                    return onBottomViewHolderCreate(loadMore);
                }
                View itemView = UIUtils.inflate(R.layout.recycler_footer, parent);
                return new BottomViewHolder(itemView);
            default:
                return onViewHolderCreate(parent, viewType);
        }

    }

    @Override
    public RecyclerView.ViewHolder onBottomViewHolderCreate(View loadMore) {
        return null;
    }

    @Override
    public void onBottomViewHolderBind(RecyclerView.ViewHolder holder, int loadState) {

    }

    @Override
    public abstract RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BOTTOM) {
            if (!isHasMore()) {
                if (loadMore != null) {
                    onBottomViewHolderBind(holder, STATE_LASTED);
                } else {
                    ((BottomViewHolder) holder).bindDateView(STATE_LASTED);
                }
                return;
            }
            if (loadMore != null) {
                onBottomViewHolderBind(holder, loadState);
            } else {
                ((BottomViewHolder) holder).bindDateView(loadState);
            }
        } else {
            onViewHolderBind(holder, position);
        }
    }

    @Override
    public abstract void onViewHolderBind(RecyclerView.ViewHolder holder, int position);

    @Override
    public final void isLoadingMore(boolean loading) {
        if (loading) {
            loadState = STATE_LOADING;
            notifyItemChanged(getItemCount());
        }
    }

    @Override
    public final int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public int getItemRealCount() {
        return list.size();
    }

    @Override
    public final void setLoadMoreView(@NonNull View view) {
        loadMore = view;
    }

    @Override
    public final int getItemViewType(int position) {
        if (list.size() > 0 && position < list.size()) {
            return getItemViewTypes(position);
        } else {
            return TYPE_BOTTOM;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewTypes(int position) {
        return 0;
    }


    @Override
    public final boolean isHasMore() {
        return getItemCount() < totalCount;
    }
}
