package com.example.common.application;

import android.content.Context;

/**
* 上下文持有者
*/
public class ContextHolder {

    private static Context sContext;

    public static void init(Context context) {
        checkContext(context);
        sContext = context;
    }

    public static Context getContext() {
        checkContext(sContext);
        return sContext;
    }

    private static void checkContext(Context context) {
        if (context == null) {
            throw new NullPointerException("The context object is null,please init first in application object!");
        }
    }

}
