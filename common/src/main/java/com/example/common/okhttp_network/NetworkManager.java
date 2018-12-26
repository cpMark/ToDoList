package com.example.common.okhttp_network;

import android.os.Handler;
import android.os.Looper;

import com.example.common.okhttp_network.builder.GetBuilder;
import com.example.common.okhttp_network.builder.HeadBuilder;
import com.example.common.okhttp_network.builder.PostFileBuilder;
import com.example.common.okhttp_network.builder.PostFormBuilder;
import com.example.common.okhttp_network.builder.PostStringBuilder;
import com.example.common.okhttp_network.callback.Callback;
import com.example.common.okhttp_network.request.RequestCall;
import com.example.common.okhttp_network.utils.ExecutorDelivery;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * function：网络请求入口类，整合了所有相关的API -- 外观模式
 * author by admin
 * create on 2018/5/16.
 */
public class NetworkManager {

    /**
     * 默认毫秒数，用于连接超时、读取超时的默认配置
     */
    public static final long DEFAULT_MILLISECONDS = 10000L;

    /**
     * 请求管理类实例--单例
     */
    private volatile static NetworkManager sInstance;

    /**
     * okhttp配置类
     */
    private OkHttpClient mOkHttpClient;

    /**
     * 响应结果线程切换辅助类
     */
    private ExecutorDelivery mExecutorDelivery;

    /**
     * 构造，初始化OkHttpClient对象
     */
    private NetworkManager(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mExecutorDelivery = new ExecutorDelivery(new Handler(Looper.getMainLooper()));
    }


    /**
     * 初始化NetworkManager对象--单例
     */
    public static NetworkManager initClient(OkHttpClient okHttpClient) {
        if (sInstance == null) {
            synchronized (NetworkManager.class) {
                if (sInstance == null) {
                    sInstance = new NetworkManager(okHttpClient);
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取NetworkManager实例，同上
     */
    public static NetworkManager getInstance() {
        return initClient(null);
    }

    /**
     * 获取OkHttpClient实例
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * --------------------------------------------  对应各种请求的Builder start  --------------------------------------------
     */

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    /** --------------------------------------------  对应各种请求的Builder end  --------------------------------------------*/


    /**
     * 执行请求(异步)
     */
    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null) {
            callback = Callback.CALLBACK_DEFAULT;
        }
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null) {
                        response.body().close();
                    }
                }

            }
        });
    }


    /**
     * 发送失败回调。同时将讲过切换到主线程
     */
    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) {
            return;
        }

        mExecutorDelivery.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    /**
     * 发送成功回调。同时将讲过切换到主线程
     */
    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) {
            return;
        }
        mExecutorDelivery.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }


    /**
     * 通过标签tag取消请求
     *
     * @param tag 请求的标签
     */
    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public ExecutorDelivery getDelivery() {
        return mExecutorDelivery;
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}
