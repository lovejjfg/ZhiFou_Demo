package com.lovejjfg.zhifou.presenters;


import android.app.Activity;
import android.util.Log;

import com.lovejjfg.zhifou.data.BaseDataManager;
import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.util.JumpUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ListPresenterImpl extends BasePresenterImpl implements ListPresenter {
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
        if (!isLoading) {
            Subscription listSubscribe = BaseDataManager.getDailyApiService().getLatestDailyStories()
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
                            mView.onLoad(dailyStories);
                            mView.isLoading(false);
                            isLoading = false;
                        }
                    });
            subscribe(listSubscribe);
        }
    }

    @Override
    public void onLoadMore(String date) {
        if (!isLoadingMore) {
            Subscription beforeSubscribe = BaseDataManager.getDailyApiService().getBeforeDailyStories(date)
                    .subscribeOn(Schedulers.io())//事件产生在子线程
                    .doOnSubscribe(new Action0() {//subscribe之后，事件发送前执行。
                        @Override
                        public void call() {
                            mView.isLoadingMore(true);
                            isLoadingMore = true;
                            Log.e("TAG", "call: true");
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<DailyStories>() {
                        @Override
                        public void call(DailyStories dailyStories) {
                            mView.onLoadMore(dailyStories);
                            mView.isLoadingMore(false);
                            Log.e("TAG", "call: false");
                            isLoadingMore = false;
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.onLoadError(throwable.toString());
                            isLoadingMore = false;
                        }
                    });
            subscribe(beforeSubscribe);
        }


    }
}
