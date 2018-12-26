package com.example.common.okhttp_network.callback;

import okhttp3.Response;

/**
 * function：字符串回调
 * author by admin
 * create on 2018/5/16.
 */
public abstract class StringCallBack extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }

}
