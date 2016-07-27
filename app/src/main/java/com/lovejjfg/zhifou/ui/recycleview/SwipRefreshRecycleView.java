package com.lovejjfg.zhifou.ui.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lovejjfg.zhifou.R;

/**
 * Created by Joe on 2016-03-11.
 * Email lovejjfg@gmail.com
 */
public class SwipRefreshRecycleView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    @Nullable
    private RecyclerView.LayoutManager manager;

    public SwipRefreshRecycleView(Context context) {
        this(context, null);
    }

    public SwipRefreshRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipRefreshRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_recycler, this, false);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recycle_view);
        mRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.container);
//        mRefreshLayout = new SwipeRefreshLayout(context);
//        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setScrollContainer(true);
//        mRefreshLayout.addView(mRecyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new FinishScrollListener());
        addView(inflate, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        invalidate();
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
        mRecyclerView.setLayoutManager(manager);

    }

    @SuppressWarnings("unused")
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);

    }


    private RefreshRecycleAdapter adapter;

    public void setAdapter(RefreshRecycleAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setRefresh(final boolean refresh) {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (null != listener) {
            listener.onRefresh();
        }
    }

    private OnRefreshLoadMoreListener listener;

    public void setOnRefreshListener(OnRefreshLoadMoreListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }


    private class FinishScrollListener extends RecyclerView.OnScrollListener {

        private int lastTitlePos = -1;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (null != scrollListener) {
                scrollListener.onScrolled(SwipRefreshRecycleView.this, dx, dy);
            }
            if (null == manager) {
                throw new RuntimeException("you should call setLayoutManager() first!!");
            }
            if (null == adapter) {
                throw new RuntimeException("you should call setAdapter() first!!");
            }
            if (manager instanceof LinearLayoutManager) {
                int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();

                if (adapter.getItemCount() > 1 && lastCompletelyVisibleItemPosition == adapter.getItemCount() - 1 && adapter.isHasMore()) {
                    if (null != listener) {
                        listener.onLoadMore();
                    }
                }
                int position = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                if (lastTitlePos == position) {
                    return;
                }
                lastTitlePos = position;
            }
            if (manager instanceof StaggeredGridLayoutManager) {
                int[] itemPositions = new int[2];
                ((StaggeredGridLayoutManager) manager).findLastVisibleItemPositions(itemPositions);

                int lastVisibleItemPosition = (itemPositions[1] != 0) ? ++itemPositions[1] : ++itemPositions[0];

                if (lastVisibleItemPosition >= adapter.getItemCount()) {
                    if (null != listener) {
                        listener.onLoadMore();
                    }
                }

            }


        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            super.onScrollStateChanged(recyclerView, newState);
            Log.e("state", newState + "");
        }
    }

    private OnScrollListener scrollListener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.scrollListener = listener;
    }

    public interface OnScrollListener {
        void onScrolled(SwipRefreshRecycleView recyclerView, int dx, int dy);
    }

}
