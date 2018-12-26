package learn.cp.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.common.local_storage.SpUtils;
import com.example.common.retrofit_network.common.BasicResponse;
import com.example.common.retrofit_network.common.Constants;
import com.example.common.retrofit_network.common.DefaultObserver;
import com.example.common.utils.RxUtil;
import com.example.common.utils.ToastUtils;
import com.example.common.utils.Utils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import learn.cp.todolist.network.RetrofitHelper;
import learn.cp.todolist.network.UserBean;

/**
 * function：登陆页
 * author by cpMark
 * create on 2018/12/18.
 */
public class LoginActivity extends RxAppCompatActivity {


    @BindView(R.id.login_et_account)
    EditText mLoginEtAccount;
    @BindView(R.id.login_et_password)
    EditText mLoginEtPassword;
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                RetrofitHelper
                        .getApiService()
                        .login(mLoginEtAccount.getText().toString().trim(), mLoginEtPassword.getText().toString().trim())
                        .compose(RxUtil.<BasicResponse<UserBean>>rxSchedulerHelper(LoginActivity.this))
                        .subscribe(new DefaultObserver<BasicResponse<UserBean>>() {
                            @Override
                            public void onSuccess(BasicResponse<UserBean> response) {
                                if (response == null || response.getData() == null || response.getCode() != Constants.SUCCESS) {
                                    ToastUtils.show(getString(R.string.login_occur_error));
                                    return;
                                }

                                SpUtils.put(getString(R.string.key_user_id), response.getData().getUserId());
                                gotoMainActivity();
                            }
                        });
                break;
            case R.id.login_btn_register:
                RetrofitHelper
                        .getApiService()
                        .registerAccount(mLoginEtAccount.getText().toString().trim(), mLoginEtPassword.getText().toString().trim())
                        .compose(RxUtil.<BasicResponse<UserBean>>rxSchedulerHelper(LoginActivity.this))
                        .subscribe(new DefaultObserver<BasicResponse<UserBean>>() {
                            @Override
                            public void onSuccess(BasicResponse<UserBean> response) {
                                if (response == null || response.getData() == null || response.getCode() != Constants.SUCCESS) {
                                    ToastUtils.show(getString(R.string.login_occur_error));
                                    return;
                                }

                                SpUtils.put(getString(R.string.key_user_id), response.getData().getUserId());
                                gotoMainActivity();
                            }
                        });
                break;
        }
    }
}
