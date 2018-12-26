package com.example.common.popup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.common.R;
import com.example.common.list.recycler_view.adapter.CommonAdapter;

/**
* PopupWindow的List视图
*/
public class ListPopupView extends LinearLayout{

    private Context mContext;
    private View mRootView;
    private RecyclerView mRvList;

    public ListPopupView(Context context) {
        this(context,null);
    }

    public ListPopupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ListPopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs){
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.pop_view_list,this);
        initView();
        initListener();
    }

    private void initView(){
        mRvList = mRootView.findViewById(R.id.rv_list);
    }

    private void initListener(){

    }

    /**
    * 设置RecyclerView的适配器
    */
    public <T> void initRv(CommonAdapter<T> adapter){
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        initRv(layoutManager,adapter);
    }

    public <T> void initRv(RecyclerView.LayoutManager layoutManager, CommonAdapter<T> adapter){
        if(layoutManager == null || adapter == null){
            throw new NullPointerException("参数为null");
        }

        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(adapter);
    }
}
