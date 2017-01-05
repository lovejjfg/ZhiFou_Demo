package com.lovejjfg.zhifou.presenters;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.data.model.Story;
import com.lovejjfg.zhifou.ui.recycleview.StoriesRecycleAdapter;
import com.lovejjfg.zhifou.util.ErrorUtil;
import com.lovejjfg.zhifou.util.JumpUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class ListPresenterImpl extends BasePresenterImpl implements ListPresenter {
    View<ListPresenter, DailyStories> mView;
    Activity activity;
    boolean isLoadingMore;
    boolean isLoading;
    ArrayList<Story> stories;

    public ListPresenterImpl(View<ListPresenter, DailyStories> view) {
        this.mView = view;
        this.activity = (Activity) view;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            onLoading();
        } else {
            List<StoriesRecycleAdapter.Item> items = savedInstanceState.getParcelableArrayList(SAVED_ITEMS);
            String date = savedInstanceState.getString(CURRENT_DATE);
            if (items != null && items.size() > 0) {
                mView.onReLoadItems(items);
                mView.onReSetDate(date);
            } else {
                onLoading();
            }
        }
    }

    @Override
    public void onItemClicked(android.view.View itemView, android.view.View image, int id) {
        JumpUtils.jumpToDetail(activity, itemView, image, id);
    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void jumpToDetail(android.view.View itemView, int id) {
//        Intent i = new Intent(activity, DetailStory.class);
//        i.putExtra(Constants.ID, id);
//        final ActivityOptions options =
//                ActivityOptions.makeSceneTransitionAnimation(activity,
//                        Pair.create(itemView,
//                                activity.getResources().getString(R.string.detail_view))
//                );
//        activity.startActivity(i, options.toBundle());
//    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoading() {
        mView.showToast("开始加载！");
        if (!isLoading) {
            Subscription listSubscribe = BaseDataManager.handleNormalService(BaseDataManager.getDailyApiService().getLatestDailyStories(), dailyStories -> {
                mView.onLoad(dailyStories);
                mView.isLoading(false);
                isLoading = false;
            }, throwable -> {
                ErrorUtil.handleError(mView, throwable, true, false);
                isLoadingMore = false;
            }, () -> {
                mView.isLoading(true);
                isLoading = true;
            });
            subscribe(listSubscribe);
        }
    }

    @Override
    public void onLoadMore(String date) {
        if (!isLoadingMore) {
            Subscription subscription = BaseDataManager.handleNormalService(BaseDataManager.getDailyApiService().getBeforeDailyStories(date),
                    dailyStories -> {
                        mView.isLoadingMore(true);
                        mView.onLoadMore(dailyStories);
                        isLoadingMore = false;
                        Log.e("TAG", "call: true");
                    }, throwable -> {
                        ErrorUtil.handleError(mView, throwable, true, false);
                        isLoadingMore = false;
                    }, () -> {
                        mView.isLoadingMore(true);
                        isLoadingMore = true;
                        Log.e("TAG", "call: true");
                    });
            subscribe(subscription);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
