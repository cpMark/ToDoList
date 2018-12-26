package com.example.common.list.recycler_view.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.common.ObjectHelper;

/**
 * function：RecyclerView通用ViewHolder
 * author by admin
 * create on 2018/4/27.
 * ViewHolder的主要作用：
 * 通过成员变量存储对应的itemView中需要操作的子View，避免每次findViewById，从而提升运行的效率
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    /**
     * 缓存我们itemView内部的子View
     * <p>
     * 因为是通用的View，对于不同的ItemType肯定没有办法确定创建哪些成员变量View，
     * 取而代之的只能是用集合来存储了
     */
    private SparseArray<View> mViews;
    private View              mRootView;
    private Context           mContext;

    ///////////////////////////////////////////////////////////////////////////
    // 实例获取
    ///////////////////////////////////////////////////////////////////////////

    public CommonViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mRootView = itemView;
        mViews = new SparseArray();
    }

    public static CommonViewHolder createViewHolder(Context context, View itemView) {
        return new CommonViewHolder(context, itemView);
    }

    public static CommonViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return CommonViewHolder.createViewHolder(context, itemView);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId 控件id
     * @return 控件
     */
    public <T extends View> T getView(int viewId) {
        //从缓存中获取View
        View view = mViews.get(viewId);
        if (view == null) {
            //如果缓存中没有对应的View，就从CommonViewHolder的itemView中获取。并添加到缓存中
            view = mRootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取根View
     */
    public View getRootView() {
        return mRootView;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 辅助方法
    ///////////////////////////////////////////////////////////////////////////

    public CommonViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setText(text);
        return this;
    }

    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setImageResource(resId);
        return this;
    }

    public CommonViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public CommonViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public CommonViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public CommonViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public CommonViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setTextColor(textColor);
        return this;
    }

    public CommonViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public CommonViewHolder setAlpha(int viewId, float value) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        }
        return this;
    }

    /**
     * 设置可见性
     */
    public CommonViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置View为可连接的（点击有链接效果）
     */
    public CommonViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 设置iconfront
     */
    public CommonViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * 为View设置进度
     */
    public CommonViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setProgress(progress);
        return this;
    }

    public CommonViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * 设置进度的最大值
     */
    public CommonViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setMax(max);
        return this;
    }

    /**
     * 设置填充的星级
     */
    public CommonViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setRating(rating);
        return this;
    }

    public CommonViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置标签
     */
    public CommonViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setTag(tag);
        return this;
    }

    public CommonViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * 设置是否可选
     */
    public CommonViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setChecked(checked);
        return this;
    }

    public CommonViewHolder setSelected(int viewId, boolean selected) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setSelected(selected);
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 点击事件
    ///////////////////////////////////////////////////////////////////////////

    public CommonViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public CommonViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public CommonViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        ObjectHelper.requireNonNull(view, "Target view is null,target view id:" + viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
