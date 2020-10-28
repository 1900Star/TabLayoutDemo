package com.yibao.tablayoutdemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Des：${BaseAdapter}
 * Time:2017/6/2 13:07
 *
 * @author Stran
 */
public abstract class BaseRvAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mList;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private OnItemListener<T> mListener;
    protected String mTag = getClass().getSimpleName() + "  ====  ";

    public BaseRvAdapter(List<T> list) {
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(), parent, false);
        return getViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindView(holder, mList.get(position));
    }

    /**
     * 子类提供一个布局ID
     *
     * @return r
     */
    protected abstract int getLayoutId();

    /**
     * 具体的视图数据绑定交给子类去做
     *
     * @param holder 子类提供一个holder
     * @param t      数据类型由子类决定
     */
    protected abstract void bindView(RecyclerView.ViewHolder holder, T t);

    /**
     * 根据子类提供的布局ID得到一个RecyclerView的Item视图，并将视图交给子类的ViewHolder
     *
     * @param view 当前RecyclerView的Item视图
     * @return d
     */
    protected abstract RecyclerView.ViewHolder getViewHolder(View view);



    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }


    public void setData(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        if (list != null) {
            for (T t : list) {
                if (!mList.contains(t)) {
                    mList.add(t);
                }
            }
            notifyDataSetChanged();
        }


    }

    public void setNewData(List<T> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    public void addData(int position, List<T> data) {
        this.mList.addAll(position, data);
        this.notifyItemRangeInserted(position, data.size());
    }


    public List<T> getData() {
        return mList;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) params;
            p.setFullSpan(holder.getLayoutPosition() == getItemCount() - 1);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOTER
                            ? gridManager.getSpanCount()
                            : 1;
                }
            });
        }
    }

    protected void openDetails(T t, int position) {
        if (mListener != null) {
            mListener.showDetailsView(t, position);
        }
    }


    public void setItemListener(OnItemListener<T> listener) {
        this.mListener = listener;
    }


    public interface OnItemListener<T> {

        /**
         * @param bean     子类的数据类型
         * @param position 当前位置
         */
        void showDetailsView(T bean, int position);

        /**
         * 显示详情页面
         *
         * @param bean
         * @param isEditStatus status
         */
//        void showDetailsView(T bean, boolean isEditStatus);
    }


}
