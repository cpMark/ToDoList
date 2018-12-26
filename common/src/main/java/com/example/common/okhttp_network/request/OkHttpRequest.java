package com.example.common.okhttp_network.request;


import com.example.common.okhttp_network.callback.Callback;
import com.example.common.okhttp_network.utils.ExceptionsUtils;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * function：Okhttp请求的基类
 * author by admin
 * create on 2018/5/16.
 */
public abstract class OkHttpRequest {

    /**
     * 请求的地址
     */
    protected String mUrl;

    /**
     * 请求标记
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
     * Okhttp请求原生类的构建者对象
     */
    protected Request.Builder mBuilder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers, int id) {
        this.mUrl = url;
        this.mTag = tag;
        this.mParams = params;
        this.mHeaders = headers;
        this.mId = id;

        if (url == null) {
            ExceptionsUtils.illegalArgument("mUrl can not be null.");
        }

        initBuilder();
    }


    /**
     * 初始化一些基本参数 mUrl , mTag , mHeaders，其它的有子类通过buildRequest来实现
     */
    private void initBuilder() {
        mBuilder
                .url(mUrl)
                //为当前请求设置标签，后面可以通过这个标签来取消请求，如果不设置，那么默认会设置自己为标签
                .tag(mTag);
        appendHeaders();
    }

    /**
     *  构建请求体
     */
    protected abstract RequestBody buildRequestBody();

    /**
     *  构建请求
     */
    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build() {
        return new RequestCall(this);
    }

    public Request generateRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    /**
     * 添加请求头
     */
    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();

        //如果请求头无效，就直接返回
        if (mHeaders == null || mHeaders.isEmpty()) {
            return;
        }

        for (String key : mHeaders.keySet()) {
            headerBuilder.add(key, mHeaders.get(key));
        }

        //将请求头添加到请求构建对象中
        mBuilder.headers(headerBuilder.build());
    }

    public int getId() {
        return mId;
    }

}
