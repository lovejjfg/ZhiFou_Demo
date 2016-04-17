package com.lovejjfg.zhifou.ui.widget.flow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lovejjfg.mychat.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 张俊 on 2016/1/9.
 */
public class NormalFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    private static final String TAG = "NormalFlowLayout";
    private static final String KEY_DEFAULT ="key_default" ;
    private static final String KEY_CHOOSE_POS ="key_choose_pos" ;
    private TagAdapter tagAdapter;
    private MotionEvent mMotionEvent;
    private boolean mAutoSelectEffect = true;


    /**
     * set the max num to checked at most!
     */
    public void setmSelectedMax(int count) {
        if (mSelectedView.size() > count) {
            Log.w(TAG, "you has already select more than " + count + " views , so it will be clear .");
            mSelectedView.clear();
        }
        mSelectedMax = count;
    }

    /**
     * set can be checked!
     *
     * @param mAutoSelectEffect
     */
    public void setmAutoSelectEffect(boolean mAutoSelectEffect) {
        this.mAutoSelectEffect = mAutoSelectEffect;
    }

    private double mSelectedMax = 5;


    public NormalFlowLayout(Context context) {
        this(context, null);
    }

    public NormalFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NormalFlowLayout);
        mAutoSelectEffect = ta.getBoolean(R.styleable.NormalFlowLayout_auto_select_effect, true);
        setClickable(mAutoSelectEffect);
        mSelectedMax = ta.getInt(R.styleable.NormalFlowLayout_max_select, 1);
        ta.recycle();
    }

    private void updateData() {
        if (null != tagAdapter) {
            for (int i = 0; i < tagAdapter.getCount(); i++) {
                /**
                 * 这里对传入的view进行包装。
                 */
                View tagView = tagAdapter.getView(this, i, tagAdapter.getItem(i));
                TagView tagViewContainer = new TagView(getContext());
                tagView.setDuplicateParentStateEnabled(true);
//                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
                tagViewContainer.addView(tagView);
                addView(tagViewContainer);
            }
        }
    }

    public void setTagAdapter(TagAdapter adapter) {
        this.tagAdapter = adapter;
        adapter.setOnDataChangedListener(this);
        mSelectedView.clear();
        updateData();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (mMotionEvent == null) return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;
        TagView child = findChild(x, y);
        int pos = findPosByView(child);
        if (child != null) {
            doSelect(child, pos);
            if (mOnTagClickListener != null) {
                return mOnTagClickListener.onTagClick(child, pos, this);
            }
        }
        return true;
    }

    private TagView findChild(int x, int y) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            TagView v = (TagView) getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            v.getHitRect(outRect);
            if (outRect.contains(x, y)) {
                return v;
            }
        }
        return null;
    }

    private int findPosByView(View child) {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    ArrayList<Integer> mSelectedView = new ArrayList<>();

    private void doSelect(TagView child, int position) {
        if (mAutoSelectEffect) {
            if (!child.isChecked()) {
                //处理max_select=1的情况
                if (mSelectedMax == 1 && mSelectedView.size() == 1) {
                    Iterator<Integer> iterator = mSelectedView.iterator();
                    Integer preIndex = iterator.next();
                    TagView pre = (TagView) getChildAt(preIndex);
                    pre.setChecked(false);
                    child.setChecked(true);
                    mSelectedView.remove(preIndex);
                    mSelectedView.add(position);
                } else {
                    if (mSelectedMax > 0 && mSelectedView.size() >= mSelectedMax)
                        return;
                    child.setChecked(true);
                    mSelectedView.add(position);
                }
            } else {
                child.setChecked(false);
                mSelectedView.remove(Integer.valueOf(position));
            }
            if (mOnSelectListener != null) {
                mOnSelectListener.onSelected(new HashSet<Integer>(mSelectedView));
            }
        }
    }

    @Override
    public void onChanged() {
        updateData();
    }

    public interface OnSelectListener {
        void onSelected(Set<Integer> selectPosSet);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
        if (mOnSelectListener != null) setClickable(true);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }

    private OnTagClickListener mOnTagClickListener;


    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
        if (onTagClickListener != null) setClickable(true);
    }

    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());

        String selectPos = "";
        if (mSelectedView.size() > 0)
        {
            for (int key : mSelectedView)
            {
                selectPos += key + "|";
            }
            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }
        bundle.putString(KEY_CHOOSE_POS, selectPos);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            String mSelectPos = bundle.getString(KEY_CHOOSE_POS);
            if (!TextUtils.isEmpty(mSelectPos))
            {
                String[] split = mSelectPos.split("\\|");
                for (String pos : split)
                {
                    int index = Integer.parseInt(pos);
                    mSelectedView.add(index);

                    TagView tagView = (TagView) getChildAt(index);
                    tagView.setChecked(true);
                }

            }
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            return;
        }
        super.onRestoreInstanceState(state);
    }

}
