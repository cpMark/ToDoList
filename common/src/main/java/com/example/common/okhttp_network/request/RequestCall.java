package com.example.common.okhttp_network.request;


import com.example.common.okhttp_network.NetworkManager;
import com.example.common.okhttp_network.callback.Callback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * function：对OkHttpRequest的封装，对外提供更多的接口：cancel0(),mReadTimeOut()...
 * 因为这些方法是所有类型共有的，所以
 * author by admin
 * create on 2018/5/16.
 */
public class RequestCall {
    /**
     *  在原生okhttp请求基础上作了简单封装的请求类对象
     */
    private OkHttpRequest mOkHttpRequest;

    /**
     *  okhttp原生请求
     */
    private Request mRequest;

    /**
     * 调用请求的对象
     */
    private Call mCall;

    /**
     *  读取超时时间
     */
    private long mReadTimeOut;

    /**
     *  写入超时时间
     */
    private long mWriteTimeOut;

    /**
     *  连接超时时间
     */
    private long mConnTimeOut;

    private OkHttpClient mClone;

    public RequestCall(OkHttpRequest request) {
        this.mOkHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.mReadTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.mWriteTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.mConnTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(Callback callback) {
        mRequest = generateRequest(callback);

        if (mReadTimeOut > 0 || mWriteTimeOut > 0 || mConnTimeOut > 0) {
            mReadTimeOut = mReadTimeOut > 0 ? mReadTimeOut : NetworkManager.DEFAULT_MILLISECONDS;
            mWriteTimeOut = mWriteTimeOut > 0 ? mWriteTimeOut : NetworkManager.DEFAULT_MILLISECONDS;
            mConnTimeOut = mConnTimeOut > 0 ? mConnTimeOut : NetworkManager.DEFAULT_MILLISECONDS;

            mClone = NetworkManager.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(mReadTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(mWriteTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(mConnTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            mCall = mClone.newCall(mRequest);
        } else {
            mCall = NetworkManager.getInstance().getOkHttpClient().newCall(mRequest);
        }
        return mCall;
    }

    private Request generateRequest(Callback callback) {
        return mOkHttpRequest.generateRequest(callback);
    }

    public void execute(Callback callback) {
        buildCall(callback);

        if (callback != null) {
            callback.onBefore(mRequest, getOkHttpRequest().getId());
        }

        NetworkManager.getInstance().execute(this, callback);
    }

    public Response execute() throws IOException {
        buildCall(null);
        return mCall.execute();
    }

    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    public Call getCall() {
        return mCall;
    }

    public Request getRequest() {
        return mRequest;
    }

    public OkHttpRequest getOkHttpRequest() {
        return mOkHttpRequest;
    }

}
