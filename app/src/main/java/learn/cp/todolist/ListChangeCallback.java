package learn.cp.todolist;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

/**
 * function：
 * author by cpMark
 * create on 2019/1/1.
 */
public class ListChangeCallback<T extends ObservableList> extends ObservableList.OnListChangedCallback<T> {

    private RecyclerView.Adapter mAdapter;

    public ListChangeCallback(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onChanged(T sender) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(T sender, int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(T sender, int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(T sender, int fromPosition, int toPosition, int itemCount) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRangeRemoved(T sender, int positionStart, int itemCount) {
        mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }
}
