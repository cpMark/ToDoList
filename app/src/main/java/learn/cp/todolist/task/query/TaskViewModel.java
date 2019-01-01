/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package learn.cp.todolist.task.query;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;


import com.example.common.application.ContextHolder;
import com.example.common.local_storage.SpUtils;
import com.example.common.retrofit_network.common.BasicResponse;
import com.example.common.retrofit_network.common.Constants;
import com.example.common.retrofit_network.common.DefaultObserver;
import com.example.common.utils.RxUtil;
import com.example.common.utils.ToastUtils;

import java.util.List;

import learn.cp.todolist.R;
import learn.cp.todolist.network.RetrofitHelper;
import learn.cp.todolist.task.add.AddTaskActivity;

/**
 * Exposes the data to be used in the task list screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class TaskViewModel extends BaseObservable {

    /**
     * 任务列表
     */
    public final ObservableList<ToDoItemBean> mUnfinishedToDoTaskList = new ObservableArrayList<>();

    /**
     * 是否加载中，默认为false
     */
    public final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    /**
     * 为了避免泄漏，要使用Application的上下文
     */
    private Context mContext;

    public TaskViewModel(Context context) {
        // 强制使用Application Context.
        mContext = context.getApplicationContext();
    }

    @Bindable
    public boolean isEmpty() {
        return mUnfinishedToDoTaskList.isEmpty();
    }

    /**
     * 查询未完成的待办任务
     */
    public void queryUnfinishedToDoTasks() {
        Integer userId = (Integer) SpUtils.get(ContextHolder.getContext().getString(R.string.key_user_id), -1);
        RetrofitHelper
                .getApiService()
                .queryUnfinishedTask(String.valueOf(userId))
                .compose(RxUtil.<BasicResponse<List<ToDoItemBean>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<ToDoItemBean>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<ToDoItemBean>> response) {
                        if (response == null || response.getData() == null || response.getCode() != Constants.SUCCESS) {
                            ToastUtils.show(ContextHolder.getContext().getString(R.string.query_to_do_task_occur_error));
                            return;
                        }

                        mUnfinishedToDoTaskList.clear();
                        mUnfinishedToDoTaskList.addAll(response.getData());
                    }
                });
    }

    public void addToDoTask(View view) {
        Intent intent = new Intent(mContext, AddTaskActivity.class);
        mContext.startActivity(intent);
    }

    @BindingAdapter("android:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view,final TaskViewModel taskViewModel) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskViewModel.loadTasks(true);
            }
        });
    }

    private void loadTasks(boolean isLoading) {
        mDataLoading.set(isLoading);
        queryUnfinishedToDoTasks();
        mDataLoading.set(false);
    }

}
