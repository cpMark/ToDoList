package com.example.common.okhttp_network.builder;

import android.net.Uri;


import com.example.common.okhttp_network.request.GetRequest;
import com.example.common.okhttp_network.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * function：GET请求的构建者 -- Builder模式
 * author by admin
 * create on 2018/5/16.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParams {

    @Override
    public RequestCall build() {
        if (mParams != null) {
            mUrl = appendParams(mUrl, mParams);
        }

        return new GetRequest(mUrl, mTag, mParams, mHeaders, mId).build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


    @Override
    public GetBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return this;
    }


}
