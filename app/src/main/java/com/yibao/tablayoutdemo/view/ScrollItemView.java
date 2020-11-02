package com.yibao.tablayoutdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoshipeng
 * createDate：2019/5/13 0013 11:39
 * className   LightScrollView
 * Des：TODO
 */
public class ScrollItemView extends HorizontalScrollView {
    private SheetScrollViewObserver mSheetScrollViewObserver;

    public ScrollItemView(Context context) {
        this(context, null);
    }

    public ScrollItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSheetScrollViewObserver = new SheetScrollViewObserver();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }

    /**
     * l:滑动之后的x的坐标。
     * t:滑动之后的y的坐标。
     * oldl:滑动之前的x坐标。
     * oldt:滑动之前的y坐标。
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mSheetScrollViewObserver != null && (l != oldl || t != oldt)) {
            mSheetScrollViewObserver.notifyAllSheetScrollChangedListener(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void addSheetScrollViewListener(OnSheetScrollChangedListener listener) {
        mSheetScrollViewObserver.addSheetScrollChangedListener(listener);
    }

    public void removeSheetScrollViewListener(OnSheetScrollChangedListener listener) {
        mSheetScrollViewObserver.removeSheetScrollChangedListener(listener);
    }

    /***
     * OnSheetScrollChangedListener接口
     */
    public interface OnSheetScrollChangedListener {
        void onSheetScrollChanged(int l, int t, int oldl, int oldt);
    }

    /**
     * 主要用于存储OnSheetScrollChangedListener，
     * 当滑动时，一起调用OnSheetScrollChangedListener中onSheetScrollChanged方法
     */
    private static class SheetScrollViewObserver {
        List<OnSheetScrollChangedListener> mListenerList;

        private SheetScrollViewObserver() {
            mListenerList = new ArrayList<>();
        }

        private void addSheetScrollChangedListener(OnSheetScrollChangedListener listener) {
            mListenerList.add(listener);
        }

        private void removeSheetScrollChangedListener(OnSheetScrollChangedListener listener) {
            mListenerList.remove(listener);
        }

        private void notifyAllSheetScrollChangedListener(int l, int t, int oldl, int oldt) {
            if (mListenerList == null || mListenerList.size() <= 0) {
                return;
            } else {
                for (int i = 0; i < mListenerList.size(); i++) {
                    OnSheetScrollChangedListener listener = mListenerList.get(i);
                    if (listener != null) {
                        listener.onSheetScrollChanged(l, t, oldl, oldt);
                    }
                }
            }
        }

    }
}
