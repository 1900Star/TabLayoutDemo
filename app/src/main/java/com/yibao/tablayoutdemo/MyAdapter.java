package com.yibao.tablayoutdemo;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.yibao.tablayoutdemo.view.ScrollItemView;

import java.util.List;

/**
 * @ Author: Luoshipeng
 * @ Name:   MyGridViewAdapter
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/6/9/ 15:13
 * @ Des:    //TODO
 */
public class MyAdapter extends BaseRvAdapter<UserInfo> {
    private ScrollItemView mLightScrollView;
    private Context mContext;

    public MyAdapter(Context context, List<UserInfo> list, ScrollItemView scrollItemView) {
        super(list);
        mContext = context;
        mLightScrollView = scrollItemView;

    }


    @Override
    protected void bindView(RecyclerView.ViewHolder holder, UserInfo userInfo) {
        if (holder instanceof HolderB) {
            HolderB excelHolder = (HolderB) holder;
            excelHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(mTag, "AAAAAAAAAAAAAAAAAAAAA");
                }
            });
            excelHolder.mLightScrollView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(mTag, "BBBBBBBBBBBBBBBBBBB");
                }
            });

            //mHeaderSheetScrollView带动ListView下面的scrollView移动
            mLightScrollView.addSheetScrollViewListener(new MySheetScrollViewListenerImp(excelHolder.mLightScrollView));
            //ListView下面scrollView的onTouch交给mHeaderSheetScrollView处理
            excelHolder.mLightScrollView.setOnTouchListener(new ListViewAttachHeadViewTouchListener());

        }

    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new HolderB(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.scroll_view_excel_item;
    }

    static class HolderB extends RecyclerView.ViewHolder {


        private final ScrollItemView mLightScrollView;

        public HolderB(View itemView) {
            super(itemView);
            mLightScrollView = itemView.findViewById(R.id.scroll_view_excel);
        }
    }

    /**
     * LightScrollView的对象包裹进来，
     * 实现接口OnSheetScrollChangedListener中onSheetScrollChanged的方法
     */
    private static class MySheetScrollViewListenerImp implements ScrollItemView.OnSheetScrollChangedListener {
        ScrollItemView mScrollView;

        private MySheetScrollViewListenerImp(ScrollItemView scrollView) {
            mScrollView = scrollView;
        }

        @Override
        public void onSheetScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollView.smoothScrollTo(l, t);
        }
    }


    /**
     * 将ListView中scrollView的OnTouch事件交给mLightScrollView
     */
    public class ListViewAttachHeadViewTouchListener implements View.OnTouchListener {
        private int mDownx;
        private int mDowny;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownx = (int) event.getX();
                    mDowny = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int movex = (int) event.getX();
                    int movey = (int) event.getY();
                    int moveDifferencex = Math.abs(movex - mDownx);
                    int moveDifference = Math.abs(movey - mDowny);
                    boolean isNeedRefresh = moveDifferencex > 10 && moveDifferencex > moveDifference;
//                    if (mContext instanceof OnLightScrollViewListener) {
//                        ((OnLightScrollViewListener) mContext).stopRefresh(!isNeedRefresh);
//                    }
                    break;
                case MotionEvent.ACTION_UP:
                    int upx = (int) event.getX();
                    int upy = (int) event.getY();
                    int xDifference = Math.abs(upx - mDownx);
                    int yDifference = Math.abs(upy - mDowny);
                    if (xDifference < 3 && yDifference < 3) {
//                        if (mContext instanceof OnLightScrollViewListener) {
//                            ((OnLightScrollViewListener) mContext).lightViewItemClick();
//                        }
                    }
                    break;
                default:
                    break;
            }
            mLightScrollView.onTouchEvent(event);
            return false;
        }
    }

}



