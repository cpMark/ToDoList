package com.example.common.mvp.support.lifecycle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;
import com.example.common.mvp.support.lifecycle.common.BindAndUnbindDelegate;
import com.example.common.mvp.support.lifecycle.common.BindAndUnbindDelegateProxy;

/**
 * function：
 * （1）Activity生命周期的目标对象（目标接口实现类）
 * （2）V层和P层绑定、解绑的代理对象
 * author by admin
 * create on 2018/5/17.
 */
public class ActivityLifecycleDelegateImpl<V extends MvpView, P extends MvpPresenter<V>> implements ActivityLifecycleDelegate<V, P> {

    private static final String KEY_VIEW_ID = "key_view_id";

    /**
     * 生命周期绑定代理对象引用声明
     */
    private BindAndUnbindDelegateProxy<V, P> mBindAndUnbindDelegateProxy;

    /**
     * 对应的Activity应用
     */
    protected AppCompatActivity mActivity;

    public ActivityLifecycleDelegateImpl(AppCompatActivity activity, BindAndUnbindDelegate delegate) {
        mActivity = activity;
        mBindAndUnbindDelegateProxy = new BindAndUnbindDelegateProxy<V, P>(delegate);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        P presenter = createPresenterInstance();

        //绑定
        mBindAndUnbindDelegateProxy.setPresenter(presenter);
        mBindAndUnbindDelegateProxy.attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mBindAndUnbindDelegateProxy.detachView();
        mBindAndUnbindDelegateProxy.getPresenter().destory();
    }

    /**
     * 创建Presenter实例
     */
    private P createPresenterInstance() {
        P presenter = mBindAndUnbindDelegateProxy.createPresenter();
        if (presenter == null) {
            //如果为空，说明客户端实现类没有创建P对象
            throw new NullPointerException("Presenter is null,please check!!!");
        }
        return presenter;
    }

    /**
     * --------------------------------------------  数据缓存声明周期方法  --------------------------------------------
     */

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {

    }
}
