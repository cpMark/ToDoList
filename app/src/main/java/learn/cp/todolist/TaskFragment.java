package learn.cp.todolist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.local_storage.SpUtils;
import com.example.common.retrofit_network.common.BasicResponse;
import com.example.common.retrofit_network.common.Constants;
import com.example.common.retrofit_network.common.DefaultObserver;
import com.example.common.utils.RxUtil;
import com.example.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import learn.cp.todolist.network.RetrofitHelper;
import learn.cp.todolist.network.ToDoItemBean;

/**
 * function：
 * author by cpMark
 * create on 2018/12/26.
 */
public class TaskFragment extends Fragment {

    @BindView(R.id.rv_todo)
    RecyclerView mRvTodo;

    private ArrayList<ToDoItemBean> mUnfinishedToDoTaskList;
    private ToDoAdapter mToDoAdapter;
    private Unbinder mBinder;

    private TaskViewModel mTaskViewModel;
    private TaskFragBinding mTaskFragBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTaskFragBinding = TaskFragBinding.inflate(inflater, container, false);

        mTaskFragBinding.setView(this);

        mTaskFragBinding.setViewmodel(mTaskViewModel);
        return inflater.inflate(R.layout.task_fragment_content, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinder = ButterKnife.bind(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        mToDoAdapter = new ToDoAdapter(getActivity().getApplicationContext(), R.layout.main_item_content, mUnfinishedToDoTaskList);
        mRvTodo.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRvTodo.setAdapter(mToDoAdapter);
    }

    private void initData() {
        mUnfinishedToDoTaskList = new ArrayList<>();
        Integer userId = (Integer) SpUtils.get(getString(R.string.key_user_id), -1);
        RetrofitHelper
                .getApiService()
                .queryUnfinishedTask(String.valueOf(userId))
                .compose(RxUtil.<BasicResponse<List<ToDoItemBean>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<ToDoItemBean>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<ToDoItemBean>> response) {
                        if (response == null || response.getData() == null || response.getCode() != Constants.SUCCESS) {
                            ToastUtils.show(getString(R.string.query_to_do_task_occur_error));
                            return;
                        }

                        mUnfinishedToDoTaskList.clear();
                        mUnfinishedToDoTaskList.addAll(response.getData());
                        mToDoAdapter.notifyDataSetChanged();
                    }
                });
    }

    public TaskFragment() {
        // Requires empty public constructor
    }

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinder.unbind();
    }
}
