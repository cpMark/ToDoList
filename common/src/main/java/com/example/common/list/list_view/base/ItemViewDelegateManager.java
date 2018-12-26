package com.example.common.list.list_view.base;


import android.support.v4.util.SparseArrayCompat;

/**
 * Created by zhy on 16/6/22.
 */
public class ItemViewDelegateManager<T> {
    SparseArrayCompat<ItemViewDelegate<T>> mDelegateArray = new SparseArrayCompat();

    public int getItemViewDelegateCount() {
        return mDelegateArray.size();
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = mDelegateArray.size();
        if (delegate != null) {
            mDelegateArray.put(viewType, delegate);
        }
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (mDelegateArray.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + mDelegateArray.get(viewType));
        }
        mDelegateArray.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = mDelegateArray.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            mDelegateArray.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = mDelegateArray.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            mDelegateArray.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = mDelegateArray.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = mDelegateArray.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return mDelegateArray.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public void convert(CommonViewHolder holder, T item, int position) {
        int delegatesCount = mDelegateArray.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = mDelegateArray.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    public int getItemViewLayoutId(int viewType) {
        return mDelegateArray.get(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return mDelegateArray.indexOfValue(itemViewDelegate);
    }

    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        int delegatesCount = mDelegateArray.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = mDelegateArray.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }
}
