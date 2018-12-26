package com.example.common.okhttp_network.request;

import android.text.TextUtils;

import com.example.common.okhttp_network.NetworkManager;
import com.example.common.okhttp_network.utils.ExceptionsUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * function：
 * author by admin
 * create on 2018/5/17.
 */
public class OtherRequest extends OkHttpRequest {
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequest(RequestBody requestBody, String content, String method, String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;

    }

    @Override
    protected RequestBody buildRequestBody() {
        if (requestBody == null && TextUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method)) {
            ExceptionsUtils.illegalArgument("requestBody and content can not be null in method:" + method);
        }

        if (requestBody == null && !TextUtils.isEmpty(content)) {
            requestBody = RequestBody.create(MEDIA_TYPE_PLAIN, content);
        }

        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        if (method.equals(NetworkManager.METHOD.PUT)) {
            mBuilder.put(requestBody);
        } else if (method.equals(NetworkManager.METHOD.DELETE)) {
            if (requestBody == null)
                mBuilder.delete();
            else
                mBuilder.delete(requestBody);
        } else if (method.equals(NetworkManager.METHOD.HEAD)) {
            mBuilder.head();
        } else if (method.equals(NetworkManager.METHOD.PATCH)) {
            mBuilder.patch(requestBody);
        }

        return mBuilder.build();
    }

}
