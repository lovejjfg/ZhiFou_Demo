/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lovejjfg.zhifou.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


public class HeadViewFrameLayout extends FrameLayout {
    private String TAG = HeadViewFrameLayout.class.getSimpleName();
    // configurable attribs
    private float dragDismissDistance = Float.MAX_VALUE;
    private float dragDismissFraction = -1f;
    private float dragDismissScale = 1f;
    private boolean shouldScale = false;
    private float dragElacticity = 0.8f;

    // state
    private float totalDrag;


    public HeadViewFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public HeadViewFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public HeadViewFrameLayout(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HeadViewFrameLayout(Context context, AttributeSet attrs,
                               int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & View.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // if we're in a drag gesture and the user reverses up the we should take those events
        Log.i(TAG, "onNestedPreScroll: " + dy);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll: " + dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.i(TAG, "onStopNestedScroll: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (dragDismissFraction > 0f) {
            dragDismissDistance = h * dragDismissFraction;
        }
    }




}
