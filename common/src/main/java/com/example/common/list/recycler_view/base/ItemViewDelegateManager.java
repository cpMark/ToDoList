package com.example.common.list.recycler_view.base;

import android.support.v4.util.SparseArrayCompat;

import com.example.common.ObjectHelper;

/**
 * 多条目类型的管理器
 * 泛型T为数据类型
 */
public class ItemViewDelegateManager<T> {

    /**
     * 条目类型的集合
     */
    SparseArrayCompat<ItemViewDelegate<T>> mDelegataArray = new SparseArrayCompat<>();

    /**
     * 获取条目类型数
     */
    public int getItemViewDelegateCount() {
        return mDelegataArray.size();
    }

    /**
     * 添加新的条目类型
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        //直接将新类型条目添加到缓存集合的最后
        int viewType = mDelegataArray.size();
        if (delegate != null) {
            mDelegataArray.put(viewType, delegate);
        }
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (mDelegataArray.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + mDelegataArray.get(viewType));
        }
        mDelegataArray.put(viewType, delegate);
        return this;
    }

    /**
     * 删除条目种类
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }

        int indexToRemove = mDelegataArray.indexOfValue(delegate);
        if (indexToRemove >= 0) {
            mDelegataArray.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = mDelegataArray.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            mDelegataArray.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 根据item和position来获取viewType
     *
     * @param item     数据类型
     * @param position 位置
     */
    public int getItemViewType(T item, int position) {
        int delegatesCount = mDelegataArray.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = mDelegataArray.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return mDelegataArray.keyAt(i);
            }
        }

        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=\" + position + \" in data source");
    }

    /**
     * 调用特定条目的回调
     */
    public void convert(CommonViewHolder holder, T item, int position) {
        int delegatesCount = mDelegataArray.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = mDelegataArray.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 根据viewType获取条目类型
     */
    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return mDelegataArray.get(viewType);
    }

    /**
     * 根据viewType获取条目的布局id
     */
    public int getItemViewLayoutId(int viewType) {
        ItemViewDelegate itemViewDelegate = getItemViewDelegate(viewType);
        ObjectHelper.requireNonNull(itemViewDelegate, "ItemViewDelegate is null");
        return itemViewDelegate.getItemViewLayoutId();
    }

    /**
     * 根据条目类型获取器viewType
     */
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return mDelegataArray.indexOfValue(itemViewDelegate);
    }

}
