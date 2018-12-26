package com.example.common.mvp.support.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.mvp.MvpPresenter;
import com.example.common.mvp.MvpView;
import com.example.common.mvp.support.lifecycle.fragment.MvpFragment;

/**
 * function：Fragment的基本实现类，除了生命周期和绑定、解绑操作，没有其他多余功能
 * author by admin
 * create on 2018/5/18.
 */
public abstract class MvpBaseFragment <V extends MvpView, P extends MvpPresenter<V>>
        extends MvpFragment<V, P> {

    /**
     *  Fragment的缓存视图
     */
    private View mCacheViewContent;

    /**
     *  是否是初始化的标识
     */
    private boolean mIsInited;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mCacheViewContent == null){
            mCacheViewContent = inflater.inflate(createViewResourceId(),container,false);
            initContentView(mCacheViewContent);
        }

        //判断Fragment对应的Activity是否存在这个视图
        ViewGroup parent = (ViewGroup) mCacheViewContent.getParent();
        if(parent != null){
            //如果存在,那么我就干掉,重写添加,这样的方式我们就可以缓存视图
            parent.removeView(mCacheViewContent);
        }

        return mCacheViewContent;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!mIsInited){
            mIsInited = true;
            initData();
        }
    }

    /**
     *  初始化数据，可根据需要来重写
     */
    public  void initData(){

    }


    /**
     *  通过传递给子类的View来初始化Fragment的视图
     */
    protected abstract void initContentView(View cacheViewContent);

    /**
     *  Fragment内容页面的xml资源id
     */
    public abstract int createViewResourceId();
}
