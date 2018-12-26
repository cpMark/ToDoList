package com.example.common.mvp.support.lifecycle.common;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;

/**
 * function：绑定和解绑V和P的目标接口（代理模式）
 * author by admin
 * create on 2018/5/17.
 */
public interface BindAndUnbindDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();

}
