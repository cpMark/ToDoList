package com.example.common.okhttp_network.utils;

/**
 * function：异常工具类
 * author by admin
 * create on 2018/5/16.
 */
public class ExceptionsUtils {

    /**
     * 不合法参数异常
     *
     * @param msg    异常信息
     * @param params 可变数组，用于传入具体参数
     */
    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

    /**
     * 空指针异常
     *
     * @param msg    异常信息
     * @param params 可变数组，用于传入具体参数
     */
    public static void nullPoint(String msg, Object... params) {
        throw new NullPointerException(String.format(msg, params));
    }

    /**
     * 不合法的状态异常
     *
     * @param msg    异常信息
     * @param params 可变数组，用于传入具体参数
     */
    public static void illegalState(String msg, Object... params) {
        throw new IllegalStateException(String.format(msg, params));
    }

}
