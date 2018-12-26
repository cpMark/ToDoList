package learn.cp.todolist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.common.utils.ActivityUtils;
import com.example.common.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private Unbinder mBind;
    private TaskViewModel mViewModel;
    public static final String TASKS_VIEWMODEL_TAG = "TASKS_VIEWMODEL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);

        TaskFragment taskFragment = findOrCreateViewFragment();

        mViewModel = findOrCreateViewModel();
        taskFragment.setViewModel(mViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @OnClick(R.id.main_fab_todo_task_add)
    public void onViewClicked() {
        // TODO: 2018/12/22 添加一个Fragment来新增待办任务
        ToastUtils.show(getString(R.string.add_todo_task));
    }

    @NonNull
    private TaskFragment findOrCreateViewFragment() {
        TaskFragment tasksFragment =
                (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = TaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.fl_content);
        }
        return tasksFragment;
    }

    private TaskViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<TaskViewModel> retainedViewModel =
                (ViewModelHolder<TaskViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(TASKS_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            TaskViewModel viewModel = new TaskViewModel(
                    Injection.provideTasksRepository(getApplicationContext()),
                    getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    TASKS_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
