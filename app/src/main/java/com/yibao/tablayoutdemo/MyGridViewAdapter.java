package com.yibao.tablayoutdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @ Author: Luoshipeng
 * @ Name:   MyGridViewAdapter
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/6/9/ 15:13
 * @ Des:    //TODO
 */
public class MyGridViewAdapter extends BaseRvAdapter<UserInfo> {

    public MyGridViewAdapter(Context context, List<UserInfo> list) {
        super(list);
    }


    @Override
    protected void bindView(RecyclerView.ViewHolder holder, UserInfo userInfo) {


    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new HolderB(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_grid_view;
    }

    static class HolderB extends RecyclerView.ViewHolder {


        public HolderB(View itemView) {
            super(itemView);

        }
    }


}



