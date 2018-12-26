package com.example.common.mvp.support.lifecycle;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * function：抽象P层的实现基类，其它的基类继承它即可，这样就不用重复写这一段逻辑
 * author by admin
 * create on 2018/5/17.
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final List<Disposable> DISPOSABLES = new ArrayList<>();

    V mView;

    public V getMvpView() {
        return mView;
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void destory() {
        recycleDisposable();
    }

    /**
    * 回收订阅
    */
    private void recycleDisposable() {
        for (Disposable disposable : DISPOSABLES) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
                disposable = null;
            }
        }
    }

    /**
    * 添加订阅
    */
    public void addDisposable(Disposable disposable) {
        if (disposable == null) {
            return;
        }

        DISPOSABLES.add(disposable);
    }


}
