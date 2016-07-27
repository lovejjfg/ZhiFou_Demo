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
    private int loadState;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    private int totalCount = Integer.MAX_VALUE;

    public RefreshRecycleAdapter() {
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }
    @Override
    public void setList(List<T> data) {
        if (data == null) {
            return;
        }
        if (list != null) {
            list.clear();
        }
        this.list = data;
    }
    @Override
    public void appendList(List<T> data) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(data);
    }

    public List<T> list;
    public static final int TYPE_BOTTOM = 400;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
//        if (list.size() == 0 || position >= list.size()) {
//            return;
//        }
        if (viewType == TYPE_BOTTOM) {
            if (loadMore != null) {
                if (!isHasMore()) {
                    loadState = STATE_LASTED;
                    onBottomViewHolderBind(holder, loadState);
                } else {
                    onBottomViewHolderBind(holder, loadState);
                }
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
    public void isLoadingMore(boolean loading) {
        if (loading) {
            loadState = STATE_LOADING;
        } else {
            loadState = STATE_DISMISS;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public void setLoadMoreView(@NonNull View view) {
        loadMore = view;
    }

    @Override
    public int getItemViewType(int position) {
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
    public abstract int getItemViewTypes(int position);

    @Override
    public boolean isHasMore() {
        return !(list.size() > 25);
    }
}
