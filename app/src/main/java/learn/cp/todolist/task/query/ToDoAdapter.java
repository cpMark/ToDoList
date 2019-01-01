package learn.cp.todolist.task.query;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import learn.cp.todolist.BR;
import learn.cp.todolist.R;

/**
 * function：
 * author by cpMark
 * create on 2018/12/21.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyHolder> {

    private List<ToDoItemBean> mDataList;

    public ToDoAdapter(List<ToDoItemBean> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ToDoAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MyHolder.create(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.MyHolder holder, int position) {
        holder.bindTo(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mViewDataBinding;

        public static MyHolder create(LayoutInflater inflater, ViewGroup parent) {
            ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.main_item_content, parent, false);
            return new MyHolder(viewDataBinding);
        }

        private MyHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
        }

        public void bindTo(ToDoItemBean toDoItemBean) {
            mViewDataBinding.setVariable(BR.todoItem, toDoItemBean);
            mViewDataBinding.executePendingBindings();
        }
    }

}
