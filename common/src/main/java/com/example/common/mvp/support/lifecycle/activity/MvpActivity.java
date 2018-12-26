package com.example.common.mvp.support.lifecycle.activity;

import android.os.Bundle;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;
import com.example.common.mvp.support.lifecycle.common.BindAndUnbindDelegate;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * function：抽象Activity基类，实现了公共逻辑
 * <p>
 * Activity生命周期的代理对象（持有目标对象的引用）
 * V层和P层绑定的目标对象
 * <p>
 * author by admin
 * create on 2018/5/17.
 */
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends RxAppCompatActivity
        implements MvpView, BindAndUnbindDelegate<V, P> {


    /**
     * 生命周期目标接口的目标实现类对象
     */
    protected ActivityLifecycleDelegate<V, P> mActivityLifecycleDelegate;

    /**
     * 通过使用时，不存在创建的方式来持有目标对象
     */
    public ActivityLifecycleDelegate<V, P> getActivityLifecycleDelegate() {
        if (mActivityLifecycleDelegate == null) {
            mActivityLifecycleDelegate = new ActivityLifecycleDelegateImpl(this, this);
        }
        return mActivityLifecycleDelegate;
    }

    /**
     * --------------------------------------------  绑定和解绑接口实现start  --------------------------------------------
     */

    private P mPresenter;

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    /** --------------------------------------------  绑定和解绑接口实现end  --------------------------------------------*/

    /**
     * --------------------------------------------  生命周期回调start  --------------------------------------------
     */

    ///////////////////////////////////////////////////////////////////////////
    // 回调目标对象的对应周期方法
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityLifecycleDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivityLifecycleDelegate().onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getActivityLifecycleDelegate().onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityLifecycleDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getActivityLifecycleDelegate().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getActivityLifecycleDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivityLifecycleDelegate().onDestroy();
    }

    /** --------------------------------------------  生命周期回调end --------------------------------------------*/

}
