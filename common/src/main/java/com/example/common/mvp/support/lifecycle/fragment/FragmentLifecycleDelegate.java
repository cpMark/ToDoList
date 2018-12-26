package com.example.common.mvp.support.lifecycle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;

/**
 * function：Fragment生命周期的目标接口
 * author by admin
 * create on 2018/5/18.
 */
public interface FragmentLifecycleDelegate<V extends MvpView,P extends MvpPresenter<V>>  {

    void onCreate(Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroyView();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onAttach(Context context);

    void onDetach();

}
