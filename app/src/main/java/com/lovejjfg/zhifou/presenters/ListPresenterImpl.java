package com.lovejjfg.zhifou.presenters;


import android.app.Activity;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.util.JumpUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class ListPresenterImpl implements ListPresenter, LifecycleCallbacks {
    View mView;
    Activity activity;
    boolean isLoadingMore;
    boolean isLoading;

    public ListPresenterImpl(View view) {
        this.mView = view;
        this.activity = (Activity) view;
        onLoading();
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onItemClicked(android.view.View itemView, android.view.View image, int id) {
        JumpUtils.jumpToDetail(activity,itemView,image, id);
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
    public void onDestroy() {


    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoading() {
        if (!isLoading) {
//            BaseDataManager.getDailyApiService().getLatestDailyStories(new Callback<DailyStories>() {
//                @Override
//                public void success(DailyStories dailyStories, Response response) {
//                    mView.onLoadMore(dailyStories);
//                    mView.isLoading(false);
//                    isLoading = false;
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    mView.onLoadError(error.toString());
//                    mView.isLoading(false);
//                    isLoading = false;
//                }
//            });
            BaseDataManager.getDailyApiService().getLatestDailyStories()
                    .subscribeOn(Schedulers.io())//事件产生在子线程
                    .doOnSubscribe(new Action0() {//subscribe之后，事件发送前执行。
                        @Override
                        public void call() {
                            mView.isLoading(true);
                            isLoading = true;
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//事件产生在子线程
                    .observeOn(AndroidSchedulers.mainThread())//
                    .subscribe(new Subscriber<DailyStories>() {
                        @Override
                        public void onCompleted() {
                            mView.isLoading(false);
                            isLoading = false;
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.isLoading(false);
                            isLoading = false;
                        }

                        @Override
                        public void onNext(DailyStories dailyStories) {
                            mView.onLoadMore(dailyStories);
                            mView.isLoading(false);
                            isLoading = false;
                        }
                    });

        }
    }

    @Override
    public void onLoadMore(String date) {
        if (!isLoadingMore) {
            mView.isLoadingMore(true);
            isLoadingMore = true;
            BaseDataManager.getDailyApiService().getBeforeDailyStories(date, new Callback<DailyStories>() {
                @Override
                public void success(DailyStories dailyStories, Response response) {
                    mView.onLoadMore(dailyStories);
                    mView.isLoadingMore(false);
                    isLoadingMore = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    mView.onLoadError(error.toString());
                    isLoadingMore = false;
                }
            });
        }


    }
}
