package com.lovejjfg.zhifou.presenters;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Joe on 2016/9/18.
 * Email lovejjfg@gmail.com
 */
public abstract class BasePresenterImpl implements BasePresenter {
    CompositeSubscription mCompositeSubscription;

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void subscribe(Subscription subscriber) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscriber);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
