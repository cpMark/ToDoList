package learn.cp.todolist.task.add;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import learn.cp.todolist.BR;
import learn.cp.todolist.R;
import learn.cp.todolist.task.query.TaskViewModel;

/**
 * function：
 * author by cpMark
 * create on 2019/1/1.
 */
public class AddTaskFragment extends Fragment {

    private AddTaskViewModel mAddTaskViewModel;
    private ViewDataBinding mViewDataBinding;

    public AddTaskFragment() {
    }

    public static AddTaskFragment newInstance() {
        return new AddTaskFragment();
    }

    public void setViewModel(AddTaskViewModel viewModel) {
        mAddTaskViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.add_task_fragment, container, false);
        mViewDataBinding.setVariable(BR.addViewModel,mAddTaskViewModel);
        return mViewDataBinding.getRoot();
    }
}
