package com.example.common.retrofit_network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头拦截器
 */

public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //  配置请求头
        String accessToken = "token";
        String tokenType = "tokenType";
        Request request = chain.request().newBuilder()
                .header("app_key", "appId")
                .header("Authorization", tokenType + " " + accessToken)
                .header("Content-Type", "application/json")
                .header("Accept-Encoding", "identity")
                .header("Connection", "close")
                .build();
        return chain.proceed(request);
    }
}
