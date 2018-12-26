package com.example.common.mvp.support.lifecycle.common;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;

/**
 * function：绑定和解绑V和P的代理对象（持有目标对象MvpBaseActivity的引用）
 * author by admin
 * create on 2018/5/17.
 */
public class BindAndUnbindDelegateProxy<V extends MvpView, P extends MvpPresenter<V>> implements BindAndUnbindDelegate<V,P> {

    private BindAndUnbindDelegate<V,P> mBindAndUnbindDelegate;

    public BindAndUnbindDelegateProxy(BindAndUnbindDelegate delegate){
        mBindAndUnbindDelegate = delegate;
    }

    @Override
    public P createPresenter() {
        P presenter = mBindAndUnbindDelegate.getPresenter();
        if(presenter == null){
            presenter = mBindAndUnbindDelegate.createPresenter();
        }

        if(presenter == null){
            throw new NullPointerException("Presenter is null!!");
        }

        mBindAndUnbindDelegate.setPresenter(presenter);

        return presenter;
    }

    @Override
    public P getPresenter() {
        return mBindAndUnbindDelegate.getPresenter();
    }

    @Override
    public void setPresenter(P presenter) {
        mBindAndUnbindDelegate.setPresenter(presenter);
    }

    @Override
    public V getMvpView() {
        return mBindAndUnbindDelegate.getMvpView();
    }

    /**
     * 绑定V和P层
     */
    public void attachView(){
        getPresenter().attachView(getMvpView());
    }

    /**
     *  解绑V和P层
     */
    public void detachView(){
        getPresenter().detachView();
    }

}
