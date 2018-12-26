package com.example.common.list.list_view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.common.list.list_view.base.CommonViewHolder;
import com.example.common.list.list_view.base.ItemViewDelegate;
import com.example.common.list.list_view.base.ItemViewDelegateManager;

import java.util.List;

/**
 * function：多条目类型的RecyclerView通用适配器
 * author by admin
 * create on 2018/4/27.
 */
public abstract class MultiItemTypeAdapter<T> extends BaseAdapter {

    public Context                 mContext;
    public List<T>                 mDataList;
    public ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 添加新的条目类型
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /**
     * 根据条目管理器实例的条目类型数是否大于0来判断是否启动条目管理器
     *
     * @return true标识启用，反之为不启用
     */
    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) {
            //不启用，直接使用默认的
            return super.getItemViewType(position);
        }

        //使用条目管理器管理类型
        return mItemViewDelegateManager.getItemViewType(mDataList.get(position), position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDataList.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        CommonViewHolder viewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new CommonViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }


        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /**
     * 转换回调
     */
    public void convert(CommonViewHolder holder, T t, int position) {
        mItemViewDelegateManager.convert(holder, t, position);
    }

    /**
     * ViewHolder创建完毕回调
     */
    public void onViewHolderCreated(CommonViewHolder holder, View itemView) {

    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList){
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
