package com.example.common.mvp.support.lifecycle.activity;

import android.os.Bundle;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;

/**
 * function：Activity生命周期的目标接口
 * author by admin
 * create on 2018/5/17.
 */
public interface ActivityLifecycleDelegate<V extends MvpView,P extends MvpPresenter<V>> {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    /** --------------------------------------------  新增数据缓存的生命周期方法  --------------------------------------------*/

    /**
     *  保存缓存对象
     */
    void onSaveInstanceState(Bundle outState);

    /**
     *  创建缓存对象
     */
    void onPostCreate(Bundle savedInstanceState);
}
