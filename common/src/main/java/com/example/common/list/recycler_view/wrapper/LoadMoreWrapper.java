package com.example.common.list.recycler_view.wrapper;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.list.recycler_view.base.CommonViewHolder;
import com.example.common.list.recycler_view.utils.WrapperUtils;

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View                 mLoadMoreView;
    private int                  mLoadMoreLayoutId;
    private OnLoadMoreListener   mOnLoadMoreListener;

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }

        return mInnerAdapter.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            CommonViewHolder holder;
            if (mLoadMoreView != null) {
                holder = CommonViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = CommonViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
                ;
            }

            return holder;
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }

            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }

                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;

            lp.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 加载更多监听接口
    ///////////////////////////////////////////////////////////////////////////

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}
