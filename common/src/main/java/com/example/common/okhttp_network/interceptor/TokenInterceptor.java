package com.example.common.okhttp_network.interceptor;

import com.example.common.local_storage.SpUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加统一参数拦截器
 */
public class TokenInterceptor implements Interceptor {

    private String mTokenKey;
    private String mUidKey;
    private String mDefaultToken;
    private String mDefaultUid;

    public TokenInterceptor(String tokenKey,String uidKey) {
        mTokenKey = tokenKey;
        mUidKey = uidKey;
        mDefaultToken = new String("");
        mDefaultUid = new String("");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = (String) SpUtils.get(mTokenKey, mDefaultToken);
        String uid = (String) SpUtils.get(mUidKey,mDefaultUid);

        Request request = chain.request();

        //参数就要针对body做操作,我这里针对From表单做操作
        if (request.body() instanceof FormBody) {
            // 构造新的请求表单
            FormBody.Builder builder = new FormBody.Builder();

            FormBody body = (FormBody) request.body();
            //将以前的参数添加
            for (int i = 0; i < body.size(); i++) {
                builder.add(body.encodedName(i), body.encodedValue(i));
            }
            //追加新的参数
            builder.add("token", token);
            builder.add("uid",uid);
            request = request.newBuilder().post(builder.build()).build();//构造新的请求体
        }

        //重新请求
        return chain.proceed(request);
    }
}
