package com.example.common.screen;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * function：获取屏幕相关属性
 * author by cpMark
 * create on 2018/5/25.
 */
public class ScreenUtil {

    /**
     *  获取屏幕相关参数
     */
    public static DisplayMetrics getScreenSize(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        return displayMetrics;
    }

    /**
     *  获取屏幕density
     */
    public static float getDeviceDensity(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        return displayMetrics.density;
    }

}
