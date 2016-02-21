package com.lovejjfg.zhifou_demo.ui.widget;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.lovejjfg.zhifou_demo.ui.WeakHandler;

/**
 * Created by 张俊 on 2016/02/14.
 */
public class TopViewPager extends ViewPager {

    private static final String TAG = TopViewPager.class.getSimpleName();
    private static final int WHAT_SCROLL = 0;
    private long mDelayTime = 5000;
    private boolean isAutoScroll;
    public boolean isStopByTouch;
    private int startX;
    private int startY;
    private WeakHandler handler;

    public TopViewPager(Context context) {
        this(context, null);
    }

    public TopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        handler = new WeakHandler(context) {
            @Override
            public void handleMessages(Message msg) {
                if (msg.what == WHAT_SCROLL) {
                    scrollOnce();
                    sendScrollMessage(mDelayTime);
                }
            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://有事件先拦截再说！！
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE://移动的时候
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                //判断四种情况：
                //3.上下互动，需要ListView来响应。
                if (Math.abs(endX - startX) < (Math.abs(endY - startY))) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if (endX - startX > 0) {//右滑动
                    if (getCurrentItem() == 0) {// 1.1 & 1.2的情况，具体逻辑和父类系统自己会处理的！
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {//2、最后一张，向左滑动，需要父亲，滑到下一个内容。
                    if (getCurrentItem() == getAdapter().getCount() - 1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            stopAutoScroll();
            isStopByTouch = true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
            startAutoScroll();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setDelayTime(int delayTime) {
        this.mDelayTime = delayTime;
    }


    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollMessage(mDelayTime);
    }

    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(WHAT_SCROLL);
    }

    public boolean isAutoScrolling() {
        return isAutoScroll;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        if (isAutoScroll) {
            handler.removeMessages(WHAT_SCROLL);
            handler.sendEmptyMessageDelayed(WHAT_SCROLL, delayTimeInMills);
        }
    }

    private void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int count;
        if (adapter == null || (count = adapter.getCount()) < 1) {
            stopAutoScroll();
            return;
        }
        if (currentItem < count) {
            currentItem++;
        }
        if (currentItem == count) {
            currentItem = 0;
        }
        Log.i(TAG, currentItem + "");
        setCurrentItem(currentItem);
    }


}
