package com.example.common.okhttp_network.builder;

import java.util.Map;

/**
 * function：Okhttp请求是否有参数的接口
 * author by admin
 * create on 2018/5/16.
 */
public interface HasParams
{
    /**
     *  添加参数集合
     */
    OkHttpRequestBuilder params(Map<String, String> params);

    /**
     *  添加参数
     */
    OkHttpRequestBuilder addParams(String key, String val);
}
