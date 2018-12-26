package com.example.common.mvp.support.lifecycle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;
import com.example.common.mvp.support.lifecycle.common.BindAndUnbindDelegate;
import com.example.common.mvp.support.lifecycle.common.BindAndUnbindDelegateProxy;

/**
 * function：
 * （1）Activity生命周期的目标对象（目标接口实现类）
 * （2）V层和P层绑定、解绑的
 * author by admin
 * create on 2018/5/18.
 */
public class FragmentLifecycleDelegateImpl<V extends MvpView,P extends MvpPresenter<V>>   implements FragmentLifecycleDelegate<V,P> {

    private BindAndUnbindDelegateProxy<V, P> mBindAndUnbindDelegate;

    public FragmentLifecycleDelegateImpl(BindAndUnbindDelegate<V,P> bindAndUnbindDelegate){
        mBindAndUnbindDelegate = new BindAndUnbindDelegateProxy<>(bindAndUnbindDelegate);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mBindAndUnbindDelegate.createPresenter();
        mBindAndUnbindDelegate.attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        mBindAndUnbindDelegate.detachView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onDetach() {

    }
}
