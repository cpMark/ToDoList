package learn.cp.todolist.login;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import learn.cp.todolist.BR;
import learn.cp.todolist.R;

/**
 * function：登陆页
 * author by cpMark
 * create on 2018/12/18.
 */
public class LoginActivity extends RxAppCompatActivity {

    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        mLoginViewModel = new LoginViewModel();
        viewDataBinding.setVariable(BR.loginViewModel, mLoginViewModel);
    }

    @Override
    protected void onDestroy() {
        mLoginViewModel.onDestroy();
        super.onDestroy();
    }
}
