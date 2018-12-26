package com.example.common.mvp.support.lifecycle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;
import com.example.common.mvp.support.lifecycle.common.BindAndUnbindDelegate;
import com.trello.rxlifecycle2.components.RxFragment;

/**
 * function：抽象Fragment基类，实现了公共逻辑
 * <p>
 * Fragment生命周期的代理对象（持有目标对象的引用）
 * V层和P层绑定的目标对象
 * <p>
 * author by admin
 * create on 2018/5/18.
 */
public class MvpFragment<V extends MvpView, P extends MvpPresenter<V>>
        extends RxFragment
        implements MvpView,BindAndUnbindDelegate<V,P> {

    private FragmentLifecycleDelegate<V,P> mFragmentLifecycleDelegate;

    public FragmentLifecycleDelegate<V, P> getFragmentLifecycleDelegate() {
        if(mFragmentLifecycleDelegate == null){
            mFragmentLifecycleDelegate = new FragmentLifecycleDelegateImpl<>(this);
        }
        return mFragmentLifecycleDelegate;
    }

    /** --------------------------------------------  绑定和解绑接口实现start  --------------------------------------------*/

    private P mPresenter;

    @Override
    public P createPresenter() {
        return mPresenter;
    }

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
        return (V)this;
    }

    /** --------------------------------------------  绑定和解绑接口实现end  --------------------------------------------*/

    /** --------------------------------------------  生命周期接口实现start  --------------------------------------------*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentLifecycleDelegate().onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentLifecycleDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getFragmentLifecycleDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentLifecycleDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFragmentLifecycleDelegate().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentLifecycleDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentLifecycleDelegate().onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        getFragmentLifecycleDelegate().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentLifecycleDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentLifecycleDelegate().onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getFragmentLifecycleDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentLifecycleDelegate().onSaveInstanceState(outState);
    }

    /** --------------------------------------------  生命周期接口实现start  --------------------------------------------*/

}
