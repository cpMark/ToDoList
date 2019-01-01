package learn.cp.todolist.task.add;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
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
import learn.cp.todolist.task.query.ToDoItemBean;

/**
 * function：
 * author by cpMark
 * create on 2019/1/1.
 */
public class AddTaskViewModel {

    /**
     * 为了避免泄漏，要使用Application的上下文
     */
    private Context mContext;

    public AddTaskViewModel(Context context) {
        // 强制使用Application Context.
        mContext = context.getApplicationContext();
    }

    /**
     * 标题
     */
    public final ObservableField<String> title = new ObservableField<>();

    /**
     * 内容
     */
    public final ObservableField<String> content = new ObservableField<>();

    /**
     *  新增待办任务
     */
    public void confirmSubmitNewTask(View view) {
        String taskTitle = this.title.get();
        String taskContent = this.content.get();
        if(TextUtils.isEmpty(taskTitle)|| TextUtils.isEmpty(taskContent)){
            ToastUtils.show(mContext.getString(R.string.add_task_params_occur_error));
            return ;
        }

        Integer userId = (Integer) SpUtils.get(mContext.getString(R.string.key_user_id), -1);
        RetrofitHelper
                .getApiService()
                .insertToDoItem(String.valueOf(userId),taskTitle,taskContent)
                .compose(RxUtil.<BasicResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<String>>() {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        if (response == null || response.getCode() != Constants.SUCCESS) {
                            ToastUtils.show(mContext.getString(R.string.add_to_do_task_occur_error));
                            return;
                        }

                        //新增成功，清空界面上的数据
                        AddTaskViewModel.this.title.set("");
                        content.set("");
                    }
                });
    }

}
