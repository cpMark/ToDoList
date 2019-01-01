package learn.cp.todolist.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.common.utils.ActivityUtils;

import learn.cp.todolist.R;
import learn.cp.todolist.task.query.TaskFragment;
import learn.cp.todolist.task.query.TaskViewModel;
import learn.cp.todolist.ViewModelHolder;

public class MainActivity extends AppCompatActivity {

    private TaskViewModel mViewModel;

    /**
     * 任务ViewModel的全局标记
     */
    public static final String TASKS_VIEWMODEL_TAG = "TASKS_VIEWMODEL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.main_activity);

        TaskFragment taskFragment = findOrCreateViewFragment();

        mViewModel = findOrCreateViewModel();
        taskFragment.setViewModel(mViewModel);
    }

    /**
     * 获取Fragment实例，如无则新建一个实例返回
     *
     * @return fragment实例
     */
    @NonNull
    private TaskFragment findOrCreateViewFragment() {
        //从容器中获取Fragment实例
        TaskFragment tasksFragment = (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (tasksFragment == null) {
            // 新建Fragment实例
            tasksFragment = TaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), tasksFragment, R.id.fl_content);
        }
        return tasksFragment;
    }

    /**
     * 获取ViewModel层实例，如无则新建一个
     *
     * @return ViewModel层实例
     */
    private TaskViewModel findOrCreateViewModel() {
        //当配置发生变化时，查询是否有保存的ViewModel实例，如有则继续使用，如无则新建
        @SuppressWarnings("unchecked")
        ViewModelHolder<TaskViewModel> retainedViewModel = (ViewModelHolder<TaskViewModel>) getSupportFragmentManager().findFragmentByTag(TASKS_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // 返回保存的实例
            return retainedViewModel.getViewmodel();
        } else {
            // 新建VM层实例
            TaskViewModel viewModel = new TaskViewModel(getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    TASKS_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
