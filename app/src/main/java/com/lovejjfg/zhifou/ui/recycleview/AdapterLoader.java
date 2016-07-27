package com.lovejjfg.zhifou.ui.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Joe on 2016-07-26
 * Email: zhangjun166@pingan.com.cn
 */
public interface AdapterLoader<T> {
    /**
     * state about load more..
     */
    int STATE_LOADING = 1;
    int STATE_DISMISS = 2;
    int STATE_LASTED = 3;
    /**
     * This method should be called  when you load more !
     */
//    void appendList(T t);

    void onBottomViewHolderBind(RecyclerView.ViewHolder holder, int loadState);
    /**
     * <p>If you want to create the specified bottom layout,you must call this method to add your specified layout !</p>
     * <p>If you don't call this method ,{@link #onBottomViewHolderBind(RecyclerView.ViewHolder, int)} will not call any way!</p>
     * @param view the specified bottom layout
     */
    void setLoadMoreView(View view);
    /**
     * If you want to create the specified bottom layout ,you should implements this method to create your own bottomViewHolder
     * and before this method called ,you should call {@link #setLoadMoreView(View)} to inflate the bottom layout at first !!!
     * @param loadMore  wether is loadingMore or not..
     */
    RecyclerView.ViewHolder onBottomViewHolderCreate(View loadMore);

    boolean isHasMore();

    void isLoadingMore(boolean loading);

    void setList(List<T> data);

    void appendList(List<T> data);

    int getItemViewTypes(int position);

    void onViewHolderBind(RecyclerView.ViewHolder holder, int position);

    RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType);

    int getItemRealCount();

}
