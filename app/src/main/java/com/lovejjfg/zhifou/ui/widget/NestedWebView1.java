package com.lovejjfg.zhifou.ui.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by 张俊 on 2016/3/12.
 */
public class NestedWebView1 extends FrameLayout implements NestedScrollingChild {
    private final NestedScrollingParentHelper mParentHelper = new NestedScrollingParentHelper(this);

    private final NestedScrollingChildHelper mChildHelper = new NestedScrollingChildHelper(this);
    private int scrollRange;
    private int activePointerId = -1;
    private double mLastMotionY;
    private double yDiff;
    private float mInitialMotionY;
    private ScrollerCompat mScroller;
    /**
     * True if the user is currently dragging this ScrollView around. This is
     * not the same as 'is being flinged', which can be checked by
     * mScroller.isFinished() (flinging begins when the user lifts his finger).
     */
    private boolean mIsBeingDragged = false;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;

    /**
     * When set to true, the scroll view measure its child to make it fill the currently
     * visible area.
     */
    private boolean mFillViewport;

    /**
     * Whether arrow scrolling is animated.
     */
    private boolean mSmoothScrollingEnabled = true;

    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = INVALID_POINTER;

    /**
     * Used during scrolling to retrieve the new offset within the window.
     */
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private int mNestedYOffset;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mScrollPointerId;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int[] mNestedOffsets;

    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */


    public NestedWebView1(Context context) {
        this(context, null);
    }

    public NestedWebView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedWebView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = ScrollerCompat.create(context, null);
    }


    private void flingWithNestedDispatch(int velocityY) {
        final int scrollY = getScrollY();
        final boolean canFling = (scrollY > 0 || velocityY > 0) &&
                (scrollY < getScrollRange() || velocityY < 0);
        if (!dispatchNestedPreFling(0, velocityY)) {
            dispatchNestedFling(0, velocityY, canFling);
            if (canFling) {
//                fling(velocityY);
            }
        }
    }



       /*NestedScrollingChild*/

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }


    public int getScrollRange() {
        scrollRange = Math.max(0,
                getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
        return scrollRange;
    }

    private static final int INVALID_POINTER = -1;

    @Override
    public boolean onInterceptHoverEvent(MotionEvent ev) {

//        final int action = ev.getAction();
//        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
//            return true;
//        }
//
//        switch (action & MotionEventCompat.ACTION_MASK) {
//            case MotionEvent.ACTION_MOVE: {
//                /*
//                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
//                 * whether the user has moved far enough from his original down touch.
//                 */
//
//                /*
//                * Locally do absolute value. mLastMotionY is set to the y value
//                * of the down event.
//                */
//                final int activePointerId = mActivePointerId;
//                if (activePointerId == INVALID_POINTER) {
//                    // If we don't have a valid id, the touch down wasn't on content.
//                    break;
//                }
//
//                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
//                if (pointerIndex == -1) {
//                    break;
//                }
//
//                final int y = (int) MotionEventCompat.getY(ev, pointerIndex);
//                final int yDiff = (int) Math.abs(y - mLastMotionY);
//                if (yDiff > mTouchSlop
//                        && (getNestedScrollAxes() & ViewCompat.SCROLL_AXIS_VERTICAL) == 0) {
//                    mIsBeingDragged = true;
//                    mLastMotionY = y;
//                    initVelocityTrackerIfNotExists();
//                    mVelocityTracker.addMovement(ev);
//                    mNestedYOffset = 0;
//                    final ViewParent parent = getParent();
//                    if (parent != null) {
//                        parent.requestDisallowInterceptTouchEvent(true);
//                    }
//                }
//                break;
//            }
//
//            case MotionEvent.ACTION_DOWN: {
//                final int y = (int) ev.getY();
////                if (!inChild((int) ev.getX(), (int) y)) {
////                    mIsBeingDragged = false;
////                    recycleVelocityTracker();
////                    break;
////                }
//
//                /*
//                 * Remember location of down touch.
//                 * ACTION_DOWN always refers to pointer index 0.
//                 */
//                mLastMotionY = y;
//                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
//
//                initOrResetVelocityTracker();
//                mVelocityTracker.addMovement(ev);
//                /*
//                * If being flinged and user touches the screen, initiate drag;
//                * otherwise don't.  mScroller.isFinished should be false when
//                * being flinged.
//                */
//                mIsBeingDragged = !mScroller.isFinished();
//                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
//                break;
//            }
//
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                /* Release the drag */
//                mIsBeingDragged = false;
//                mActivePointerId = INVALID_POINTER;
//                recycleVelocityTracker();
//                if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
//                    ViewCompat.postInvalidateOnAnimation(this);
//                }
//                stopNestedScroll();
//                break;
//            case MotionEventCompat.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                break;
//        }

        /*
        * The only time we want to intercept motion events is if we are in the
        * drag mode.
        */
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTrackerIfNotExists();

        MotionEvent vtev = MotionEvent.obtain(ev);

        final int actionMasked = MotionEventCompat.getActionMasked(ev);
//
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0;
        }
        vtev.offsetLocation(0, mNestedYOffset);
//
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
//                mScrollPointerId = MotionEventCompat.getPointerId(ev, 0);
//                mInitialTouchX = mLastTouchX = (int) (ev.getX() + 0.5f);
//                mInitialTouchY = mLastTouchY = (int) (ev.getY() + 0.5f);
//
//                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
//                nestedScrollAxis |= ViewCompat.SCROLL_AXIS_VERTICAL;
//                startNestedScroll(nestedScrollAxis);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                final int index = MotionEventCompat.findPointerIndex(ev, mScrollPointerId);
//                if (index < 0) {
//                    return false;
//                }
//
//                final int x = (int) (MotionEventCompat.getX(ev, index) + 0.5f);
//                final int y = (int) (MotionEventCompat.getY(ev, index) + 0.5f);
//                int dx = mLastTouchX - x;
//                int dy = mLastTouchY - y;
//
//                if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset)) {
//                    dx -= mScrollConsumed[0];
//                    dy -= mScrollConsumed[1];
//                    vtev.offsetLocation(mScrollOffset[0], mScrollOffset[1]);
//                    // Updated the nested offsets
//                    mNestedOffsets[0] += mScrollOffset[0];
//                    mNestedOffsets[1] += mScrollOffset[1];
//                }
//
//                if (mIsBeingDragged) {
//                    boolean startScroll = false;
//                    if (Math.abs(dy) > mTouchSlop) {
//                        if (dy > 0) {
//                            dy -= mTouchSlop;
//                        } else {
//                            dy += mTouchSlop;
//                        }
//                        startScroll = true;
//                    }
//
//                }
//
//                if (mIsBeingDragged) {
//                    mLastTouchX = x - mScrollOffset[0];
//                    mLastTouchY = y - mScrollOffset[1];
//
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                break;

                /*,,,,,*/
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                if (activePointerIndex == -1) {
                    break;
                }

                final int y = (int) MotionEventCompat.getY(ev, activePointerIndex);
                int deltaY = (int) (mLastMotionY - y);
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1];
                    vtev.offsetLocation(0, mScrollOffset[1]);
                    mNestedYOffset += mScrollOffset[1];
                }
                if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    mIsBeingDragged = true;
                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                }
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    mLastMotionY = y - mScrollOffset[1];

                    final int oldY = getScrollY();
                    final int range = getScrollRange();
                    final int overscrollMode = ViewCompat.getOverScrollMode(this);
                    boolean canOverscroll = overscrollMode == ViewCompat.OVER_SCROLL_ALWAYS ||
                            (overscrollMode == ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS &&
                                    range > 0);

                    // Calling overScrollByCompat will call onOverScrolled, which
                    // calls onScrollChanged if applicable.
                    if (overScrollByCompat(0, deltaY, 0, getScrollY(), 0, range, 0,
                            0, true) && !hasNestedScrollingParent()) {
                        // Break our velocity if we hit a scroll barrier.
                        mVelocityTracker.clear();
                    }

                    final int scrolledDeltaY = getScrollY() - oldY;
                    final int unconsumedY = deltaY - scrolledDeltaY;
                    if (dispatchNestedScroll(0, scrolledDeltaY, 0, unconsumedY, mScrollOffset)) {
                        mLastMotionY -= mScrollOffset[1];
                        vtev.offsetLocation(0, mScrollOffset[1]);
                        mNestedYOffset += mScrollOffset[1];
                    } else if (canOverscroll) {
                        ensureGlows();
                        final int pulledToY = oldY + deltaY;
                        if (pulledToY < 0) {
                            mEdgeGlowTop.onPull((float) deltaY / getHeight(),
                                    MotionEventCompat.getX(ev, activePointerIndex) / getWidth());
                            if (!mEdgeGlowBottom.isFinished()) {
                                mEdgeGlowBottom.onRelease();
                            }
                        } else if (pulledToY > range) {
                            mEdgeGlowBottom.onPull((float) deltaY / getHeight(),
                                    1.f - MotionEventCompat.getX(ev, activePointerIndex)
                                            / getWidth());
                            if (!mEdgeGlowTop.isFinished()) {
                                mEdgeGlowTop.onRelease();
                            }
                        }
                        if (mEdgeGlowTop != null
                                && (!mEdgeGlowTop.isFinished() || !mEdgeGlowBottom.isFinished())) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker,
                            mActivePointerId);

                    if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                        flingWithNestedDispatch(-initialVelocity);
                    } else if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0,
                            getScrollRange())) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
                mActivePointerId = INVALID_POINTER;
                endDrag();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged && getChildCount() > 0) {
                    if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0,
                            getScrollRange())) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
                mActivePointerId = INVALID_POINTER;
                endDrag();
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionY = (int) MotionEventCompat.getY(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                mLastMotionY = (int) MotionEventCompat.getY(ev,
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId));
                break;
        }

        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */

    public boolean canChildScrollUp(View mTarget) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private EdgeEffectCompat mEdgeGlowTop;
    private EdgeEffectCompat mEdgeGlowBottom;

    private void ensureGlows() {
        if (ViewCompat.getOverScrollMode(this) != ViewCompat.OVER_SCROLL_NEVER) {
            if (mEdgeGlowTop == null) {
                Context context = getContext();
                mEdgeGlowTop = new EdgeEffectCompat(context);
                mEdgeGlowBottom = new EdgeEffectCompat(context);
            }
        } else {
            mEdgeGlowTop = null;
            mEdgeGlowBottom = null;
        }
    }


    boolean overScrollByCompat(int deltaX, int deltaY,
                               int scrollX, int scrollY,
                               int scrollRangeX, int scrollRangeY,
                               int maxOverScrollX, int maxOverScrollY,
                               boolean isTouchEvent) {
        final int overScrollMode = ViewCompat.getOverScrollMode(this);
        final boolean canScrollHorizontal =
                computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        final boolean canScrollVertical =
                computeVerticalScrollRange() > computeVerticalScrollExtent();
        final boolean overScrollHorizontal = overScrollMode == ViewCompat.OVER_SCROLL_ALWAYS ||
                (overScrollMode == ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollHorizontal);
        final boolean overScrollVertical = overScrollMode == ViewCompat.OVER_SCROLL_ALWAYS ||
                (overScrollMode == ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollVertical);

        int newScrollX = scrollX + deltaX;
        if (!overScrollHorizontal) {
            maxOverScrollX = 0;
        }

        int newScrollY = scrollY + deltaY;
        if (!overScrollVertical) {
            maxOverScrollY = 0;
        }

        // Clamp values if at the limits and record
        final int left = -maxOverScrollX;
        final int right = maxOverScrollX + scrollRangeX;
        final int top = -maxOverScrollY;
        final int bottom = maxOverScrollY + scrollRangeY;

        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }

        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }

        if (clampedY) {
            mScroller.springBack(newScrollX, newScrollY, 0, 0, 0, getScrollRange());
        }

        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);

        return clampedX || clampedY;
    }

    private void endDrag() {
        mIsBeingDragged = false;

        recycleVelocityTracker();
        stopNestedScroll();

        if (mEdgeGlowTop != null) {
            mEdgeGlowTop.onRelease();
            mEdgeGlowBottom.onRelease();
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>
                MotionEventCompat.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            // TODO: Make this decision more intelligent.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionY = (int) MotionEventCompat.getY(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

}
