package com.example.common.popup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.R;

/**
* PopupWindow 的Tip视图
*/
public class TipPopupView extends LinearLayout {

    private Context  mContext;
    private View     mRootView;
    private TextView mTvContent;
    private TextView mTvConfirm;
    private TextView mTvCancel;

    public TipPopupView(Context context) {
        this(context,null);
    }

    public TipPopupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init(){
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.pop_view_tip, this);
        initView();
        initListener();
    }

    private void initView() {
        mTvContent = mRootView.findViewById(R.id.tv_tip_content);
        mTvConfirm = mRootView.findViewById(R.id.tv_confirm);
        mTvCancel = mRootView.findViewById(R.id.tv_cancel);
    }

    private void initListener() {
        //默认的dismiss操作是回收PopupWindow
        mTvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowManager.getInstance().destroy();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // 设置文本
    ///////////////////////////////////////////////////////////////////////////

    public void setTipContent(String tipContent){
        if(mTvContent == null || TextUtils.isEmpty(tipContent)){
            return ;
        }
        mTvContent.setText(tipContent);
    }

//    public void setConfirmBtnText(String confirmBtnText){
//        if(mTvConfirm == null || TextUtils.isEmpty(confirmBtnText)){
//            return ;
//        }
//        mTvConfirm.setText(confirmBtnText);
//    }

//    public void setDismissBtnText(String dismissBtnText){
//        if(mTvCancel == null || TextUtils.isEmpty(dismissBtnText)){
//            return ;
//        }
//        mTvCancel.setText(dismissBtnText);
//    }

    ///////////////////////////////////////////////////////////////////////////
    // 设置按钮监听事件
    ///////////////////////////////////////////////////////////////////////////

    public void setConfirmBtnListener(View.OnClickListener listener){
        if(mTvConfirm == null || listener == null){
            return ;
        }
        mTvConfirm.setOnClickListener(listener);
    }

    public void setDismissBtnListener(View.OnClickListener listener){
        if(mTvCancel == null || listener == null){
            return ;
        }
        mTvCancel.setOnClickListener(listener);
    }
}
