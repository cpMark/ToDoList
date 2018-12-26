package com.example.common.list.recycler_view.adapter;

import android.content.Context;
import android.view.LayoutInflater;


import com.example.common.list.recycler_view.base.CommonViewHolder;
import com.example.common.list.recycler_view.base.ItemViewDelegate;

import java.util.List;

/**
 * function：RecyclerView通用Adapter（针对单条目）
 * author by admin
 * create on 2018/4/27.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public int            mLayoutId;
    public LayoutInflater mInflater;


    public CommonAdapter(final Context context, final int layoutId, List<T> dataList) {
        super(context, dataList);
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;

        //添加单条目
        addItemViewDelegate(new ItemViewDelegate<T>() {

            @Override
            public int getItemViewLayoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(CommonViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    public abstract void convert(CommonViewHolder holder, T t, int position);

}
