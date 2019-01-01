package learn.cp.todolist.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.common.dialog.DialogUtils;
import com.example.common.local_storage.SpUtils;
import com.example.common.retrofit_network.common.BasicResponse;
import com.example.common.retrofit_network.common.Constants;
import com.example.common.retrofit_network.common.DefaultObserver;
import com.example.common.utils.ActivityUtils;
import com.example.common.utils.RxUtil;
import com.example.common.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import learn.cp.todolist.R;
import learn.cp.todolist.main.MainActivity;
import learn.cp.todolist.network.RetrofitHelper;

/**
 * function：登陆界面的ViewModel
 * author by cpMark
 * create on 2018/12/31.
 */
public class LoginViewModel extends BaseObservable {

    /**
     * account : cpp
     * pwd : 123456
     * userId : 2
     */

    private ObservableField<String> account = new ObservableField<>();
    private ObservableField<String> pwd = new ObservableField<>();
    private ObservableField<Integer> userId = new ObservableField<>();

    public String getAccount() {
        return account.get();
    }

    public void setAccount(String account) {
        this.account.set(account);
    }

    public String getPwd() {
        return pwd.get();
    }

    public void setPwd(String pwd) {
        this.pwd.set(pwd);
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    /**
     * 登陆
     *
     * @param view 登陆按钮
     */
    public void login(final View view) {
        RetrofitHelper
                .getApiService()
                .login(account.get(), pwd.get())
                .compose(RxUtil.<BasicResponse<UserBean>>rxSchedulerHelper((LoginActivity) view.getContext()))
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>() {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        if (response == null || response.getData() == null || response.getCode() != Constants.SUCCESS) {
                            loginFail(view.getContext().getString(R.string.login_occur_error));
                            return;
                        }

                        SpUtils.put(view.getContext().getString(R.string.key_user_id), response.getData().getUserId());
                        gotoMainActivity(view.getContext());
                    }
                });
    }

    /**
     * 注册
     */
    public void register(final View view) {
        RetrofitHelper
                .getApiService()
                .registerAccount(account.get(), pwd.get())
                .compose(RxUtil.<BasicResponse<UserBean>>rxSchedulerHelper(((LoginActivity) view.getContext())))
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>() {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        if (response == null || response.getData() == null || response.getCode() != Constants.SUCCESS) {
                            registerFail(view.getContext().getString(R.string.login_occur_error));
                            return;
                        }

                        SpUtils.put(view.getContext().getString(R.string.key_user_id), response.getData().getUserId());
                        gotoMainActivity(view.getContext());
                    }
                });
    }

    /**
     * 登陆失败
     */
    private void loginFail(String tipMessage) {
        ToastUtils.show(tipMessage);
    }

    /**
     * 注册失败
     */
    private void registerFail(String tipMessage) {
        ToastUtils.show(tipMessage);
    }

    /**
     * 跳转到下一个界面
     */
    private void gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((LoginActivity) context).finish();
    }

    public void onDestroy() {
        // TODO: 2019/1/1 资源回收
    }
}
