package com.example.common.list.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.ObjectHelper;
import com.example.common.list.recycler_view.base.CommonViewHolder;
import com.example.common.list.recycler_view.base.ItemViewDelegate;
import com.example.common.list.recycler_view.base.ItemViewDelegateManager;

import java.util.List;

/**
 * function：多条目类型的RecyclerView通用适配器
 * author by admin
 * create on 2018/4/27.
 */
public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    public Context                 mContext;
    public List<T>                 mDataList;
    public ItemViewDelegateManager mItemViewDelegateManager;
    public OnItemClickListener     mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;

    public MultiItemTypeAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
        mItemViewDelegateManager = new ItemViewDelegateManager();
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
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        //因为子类默认需要调用addItemViewDelegate来添加条目种类，所以此处一般不会为null
        ObjectHelper.requireNonNull(itemViewDelegate, "ItemViewDelegate is null");
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        CommonViewHolder holder = CommonViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getRootView());
        setListener(parent, holder, viewType);
        return holder;
    }

    /**
     * ViewHolder创建完毕回调
     */
    public void onViewHolderCreated(CommonViewHolder holder, View itemView) {

    }

    protected void setListener(ViewGroup parent, final CommonViewHolder commonViewHolder, int viewType) {
        if (!isEnable(viewType)) {
            return;
        }

        //设置点击监听器
        commonViewHolder.getRootView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = commonViewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, commonViewHolder, position);
                }
            }
        });

        //设置长按监听器
        commonViewHolder.getRootView().setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = commonViewHolder.getAdapterPosition();
                    return mOnItemLongClickListener.onItemLongClick(v, commonViewHolder, position);
                }
                return false;
            }
        });
    }

    /**
     * 默认所有条目都是可以设置监听器的
     */
    protected boolean isEnable(int viewType) {
        return true;
    }

    /**
     * 转换回调
     */
    public void convert(CommonViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        convert(holder, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList){
        mDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 添加新的条目类型
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 可实现添加新条目，也可以替换已有条目
     */
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

    ///////////////////////////////////////////////////////////////////////////
    // 点击事件
    ///////////////////////////////////////////////////////////////////////////

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

}
