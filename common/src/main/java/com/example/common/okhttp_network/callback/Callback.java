package com.example.common.okhttp_network.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * function：请求回调类
 * author by admin
 * create on 2018/5/16.
 */
public abstract class Callback<T> {

    /**
     * 请求钱--主线程
     *
     * @param request 请求
     * @param id      请求id
     */
    public void onBefore(Request request, int id) {
    }

    /**
     * 请求完成后--主线程
     *
     * @param id 请求id
     */
    public void onAfter(int id) {
    }

    /**
     * 请求中--主线程
     *
     * @param progress 当前进度
     * @param total    总大小
     * @param id       请求id
     */
    public void inProgress(float progress, long total, int id) {

    }

    /**
     * 判断当前是否响应成功，成功返回true。当我们正在解析网络返回的响应时，也是成功的
     *
     * @param response 请求的响应
     * @param id       请求的id
     * @return 当前请求是否成功的标识，true标识成功，反之失败
     */
    public boolean validateReponse(Response response, int id) {
        return response.isSuccessful();
    }

    /**
     * 在子线程解析响应结果
     *
     * @param response 网络请求响应
     */
    public abstract T parseNetworkResponse(Response response, int id) throws Exception;

    /**
     *  失败回调
     */
    public abstract void onError(Call call, Exception e, int id);

    /**
     *  成功回调
     */
    public abstract void onResponse(T response, int id);


    /**
     *  默认的请求对调实现（在使用者传入为null时）
     */
    public static Callback CALLBACK_DEFAULT = new Callback() {

        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };

}