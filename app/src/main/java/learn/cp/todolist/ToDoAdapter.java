package learn.cp.todolist;

import android.content.Context;
import android.widget.TextView;

import com.example.common.list.recycler_view.adapter.CommonAdapter;
import com.example.common.list.recycler_view.base.CommonViewHolder;

import java.util.List;

import learn.cp.todolist.network.ToDoItemBean;

/**
 * function：
 * author by cpMark
 * create on 2018/12/21.
 */
public class ToDoAdapter extends CommonAdapter<ToDoItemBean> {

    public ToDoAdapter(Context context, int layoutId, List<ToDoItemBean> dataList) {
        super(context, layoutId, dataList);
    }

    @Override
    public void convert(CommonViewHolder holder, ToDoItemBean toDoItemBean, int position) {
        ((TextView) holder.getView(R.id.main_tv_todo_title)).setText(toDoItemBean.getTitle());
        ((TextView) holder.getView(R.id.main_tv_todo_content)).setText(toDoItemBean.getContent());
        ((TextView) holder.getView(R.id.main_tv_todo_priority)).setText(String.valueOf(toDoItemBean.getPriority()));
    }
}
