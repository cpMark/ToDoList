package com.example.common.retrofit_network.common;

/**
 * 网络请求的参数配置
 */

public interface Constants {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 30000;

    /**
     *  成功
     */
    int SUCCESS = 1;

    /**
     *  失败
     */
    int ERROR = 0;

    /**
     *  登陆失效
     */
    int LOGIN_LOSE_EFFICACY = 5;

    /**
     * 地址
     */
    String HOST = "http://xxx/todolist/";
}
