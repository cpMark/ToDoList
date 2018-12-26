package com.example.common.okhttp_network.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * function：Get请求
 * author by admin
 * create on 2018/5/16.
 */
public class GetRequest extends OkHttpRequest {

    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
    }

    /**
     * Get请求没有请求体
     */
    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    /**
     * 构建ohttp原生请求对象
     */
    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.get().build();
    }


}
