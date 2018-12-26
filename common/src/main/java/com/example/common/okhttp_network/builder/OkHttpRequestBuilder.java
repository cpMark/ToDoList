package com.example.common.okhttp_network.builder;


import com.example.common.okhttp_network.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * function：Okhttp请求的构建者基类 -- 抽象类
 * author by admin
 * create on 2018/5/16.
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {

    /**
     * 请求的地址
     */
    protected String mUrl;

    /**
     * 请求标签
     */
    protected Object mTag;

    /**
     * 请求头
     */
    protected Map<String, String> mHeaders;

    /**
     * 请求参数
     */
    protected Map<String, String> mParams;

    /**
     * 请求id
     */
    protected int mId;

    /**
     *  设置id
     */
    public T id(int id) {
        this.mId = id;
        return (T) this;
    }

    /**
     *  设置url
     */
    public T url(String url) {
        this.mUrl = url;
        return (T) this;
    }


    /**
     *  设置标签
     */
    public T tag(Object tag) {
        this.mTag = tag;
        return (T) this;
    }

    /**
     *  设置请求头
     */
    public T headers(Map<String, String> headers) {
        this.mHeaders = headers;
        return (T) this;
    }

    /**
     *  添加请求头（当请求头key-value较少时可以使用）
     */
    public T addHeader(String key, String val) {
        if (this.mHeaders == null) {
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
