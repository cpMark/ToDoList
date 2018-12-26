package com.example.common.okhttp_network.builder;


import com.example.common.okhttp_network.NetworkManager;
import com.example.common.okhttp_network.request.OtherRequest;
import com.example.common.okhttp_network.request.RequestCall;

/**
 * function：Head请求构建者
 * author by admin
 * create on 2018/5/17.
 */
public class HeadBuilder extends GetBuilder{

    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, NetworkManager.METHOD.HEAD, mUrl, mTag, mParams, mHeaders,mId).build();
    }
}
