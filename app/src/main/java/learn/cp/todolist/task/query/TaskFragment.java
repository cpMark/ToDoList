package learn.cp.todolist.task.query;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import learn.cp.todolist.BR;
import learn.cp.todolist.ListChangeCallback;
import learn.cp.todolist.R;

/**
 * function：
 * author by cpMark
 * create on 2018/12/26.
 */
public class TaskFragment extends RxFragment {

    private ToDoAdapter mToDoAdapter;

    private TaskViewModel mTaskViewModel;
    private ViewDataBinding mTaskFragBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTaskFragBinding = DataBindingUtil.inflate(inflater, R.layout.query_task_fragment, container, false);

        mTaskFragBinding.setVariable(BR.taskViewModel, mTaskViewModel);
        return mTaskFragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        mToDoAdapter = new ToDoAdapter(mTaskViewModel.mUnfinishedToDoTaskList);
        RecyclerView rvTodo = mTaskFragBinding.getRoot().findViewById(R.id.rv_todo);
        rvTodo.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvTodo.setAdapter(mToDoAdapter);
        mTaskViewModel.mUnfinishedToDoTaskList.addOnListChangedCallback(new ListChangeCallback(mToDoAdapter));
    }

    private void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskViewModel.queryUnfinishedToDoTasks();
    }

    public TaskFragment() {

    }

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    public void setViewModel(TaskViewModel viewModel) {
        mTaskViewModel = viewModel;
    }
}
