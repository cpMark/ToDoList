package com.example.common.popup;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.common.R;


/**
 * function：PopupWindow管理类
 * author by admin
 * create on 2018/4/28.
 */
public class PopupWindowManager {

    private static volatile PopupWindowManager sInstance;
    private AppCompatActivity mActivity;
    private                 View               mPopupView;
    private                 PopupWindow        mPopupWindow;

    private PopupWindow.OnDismissListener mDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (mActivity == null || mActivity.isFinishing()) {
                mActivity = null;
                return;
            }

            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 1f;
            mActivity.getWindow().setAttributes(lp);
        }
    };

    private PopupWindowManager() {
    }

    public static PopupWindowManager getInstance() {
        if (sInstance == null) {
            synchronized (PopupWindowManager.class) {
                if (sInstance == null) {
                    sInstance = new PopupWindowManager();
                }
            }
        }

        return sInstance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // showAtLocation
    ///////////////////////////////////////////////////////////////////////////

    public void showPopAtLocation(AppCompatActivity activity, View locationView, View popView) {
        showPopAtLocation(activity, locationView, popView, Gravity.BOTTOM, true, true);
    }

    public void showPopAtLocation(AppCompatActivity activity, View locationView, View popView, int gravity, boolean focusable, boolean isNeedAnimation) {
        if (!handleParams(activity, popView, focusable, isNeedAnimation)) {
            return;
        }
        //处理背景
        handlePopupWindowBg();

        if (locationView == null) {
            mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            mPopupWindow.showAtLocation(locationView, gravity, 0, 0);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // showAsDropDown
    ///////////////////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showPopAsDropDown(AppCompatActivity activity, View locationView, View popView, boolean focusable, boolean isNeedAnimation) {
        if (!handleParams(activity, popView, focusable, isNeedAnimation)) {
            return;
        }
        //处理背景
        handlePopupWindowBg();

        mPopupWindow.showAsDropDown(locationView, computePopupX(locationView, popView), 0, Gravity.CENTER_HORIZONTAL);
    }

    /**
    * 计算PopupWindow内容视图的x坐标
    */
    private int computePopupX(View locationView, View popView) {
        popView.measure(0, 0);
        return locationView.getWidth() / 2 - popView.getMeasuredWidth() / 2 - locationView.getPaddingLeft();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showPopAsDropDown(AppCompatActivity activity, View locationView, View popView) {
        showPopAsDropDown(activity, locationView, popView, true, true);
    }

    /**
     * 处理参数
     *
     * @Return 返回true表示参数无问题，反之为false
     */
    private boolean handleParams(AppCompatActivity activity, View popView) {
        if (activity == null || activity.isFinishing() || popView == null) {
            return false;
        }

        mActivity = activity;
        mPopupView = popView;
        mPopupWindow = null;

        if (mPopupWindow == null) {
            mPopupWindow = createPopupWindow();
        }

        return true;
    }

    private boolean handleParams(AppCompatActivity activity, View popView, boolean focusable, boolean isNeedAnimation) {
        if (activity == null || activity.isFinishing() || popView == null) {
            return false;
        }

        mActivity = activity;
        mPopupView = popView;
        mPopupWindow = null;

        if (mPopupWindow == null) {
            mPopupWindow = createPopupWindow(focusable, isNeedAnimation);
        }

        return true;
    }


    /**
     * 处理PopupWindow的背景
     */
    private void handlePopupWindowBg() {
        ColorDrawable cd = new ColorDrawable(0x999999);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        mActivity.getWindow().setAttributes(lp);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    private PopupWindow createPopupWindow() {
        return createPopupWindow(true, true);
    }

    private PopupWindow createPopupWindow(boolean focusable, boolean isNeedAnimation) {
        PopupWindow popupWindow = new PopupWindow(mPopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, focusable);
        if (isNeedAnimation) {
            popupWindow.setAnimationStyle(R.style.PopupAnimation);
        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(mDismissListener);
        popupWindow.update();

        return popupWindow;
    }

    public void hide() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 当前PopupWindow是否是显示状态
     *
     * @return true表示PopupWindow为显示状态，反之为false
     */
    public boolean isShowStatus() {
        if (mPopupWindow == null) {
            return false;
        }

        if (mPopupWindow.isShowing()) {
            return true;
        }
        return false;
    }

    public void destroy() {
        hide();
        mPopupWindow = null;
        mPopupView = null;
        if (mActivity == null || mActivity.isFinishing()) {
            mActivity = null;
        }
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }
}
