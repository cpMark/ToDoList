package learn.cp.todolist.task.add;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.common.utils.ActivityUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import learn.cp.todolist.R;
import learn.cp.todolist.ViewModelHolder;
import learn.cp.todolist.task.query.TaskFragment;
import learn.cp.todolist.task.query.TaskViewModel;

/**
 * function：
 * author by cpMark
 * create on 2019/1/1.
 */
public class AddTaskActivity extends RxAppCompatActivity {

    private AddTaskViewModel mViewModel;

    /**
     * 任务ViewModel的全局标记
     */
    public static final String ADD_TASKS_VIEWMODEL_TAG = "ADD_TASKS_VIEWMODEL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.add_task_activity);

        AddTaskFragment addTaskFragment = findOrCreateViewFragment();

        mViewModel = findOrCreateViewModel();
        addTaskFragment.setViewModel(mViewModel);
    }

    /**
     * 获取Fragment实例，如无则新建一个实例返回
     *
     * @return fragment实例
     */
    @NonNull
    private AddTaskFragment findOrCreateViewFragment() {
        //从容器中获取Fragment实例
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (addTaskFragment == null) {
            // 新建Fragment实例
            addTaskFragment = AddTaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addTaskFragment, R.id.fl_content);
        }
        return addTaskFragment;
    }

    /**
     * 获取ViewModel层实例，如无则新建一个
     *
     * @return ViewModel层实例
     */
    private AddTaskViewModel findOrCreateViewModel() {
        //当配置发生变化时，查询是否有保存的ViewModel实例，如有则继续使用，如无则新建
        @SuppressWarnings("unchecked")
        ViewModelHolder<AddTaskViewModel> retainedViewModel = (ViewModelHolder<AddTaskViewModel>) getSupportFragmentManager().findFragmentByTag(ADD_TASKS_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // 返回保存的实例
            return retainedViewModel.getViewmodel();
        } else {
            // 新建VM层实例
            AddTaskViewModel viewModel = new AddTaskViewModel(getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    ADD_TASKS_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
