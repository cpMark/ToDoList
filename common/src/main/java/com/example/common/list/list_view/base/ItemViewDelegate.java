package com.example.common.list.list_view.base;

/**
 * ItemView目标接口
 * 泛型T为数据类型
 */
public interface ItemViewDelegate<T> {

    /**
     * 条目的布局id
     */
    int getItemViewLayoutId();

    /**
    * 当前position和对应的条目数据是否符合条件
    */
    boolean isForViewType(T item, int position);

    /**
     * 回调
     */
    void convert(CommonViewHolder holder, T t, int position);

}
