package com.example.common.okhttp_network.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * function：Okhttp的网络请求日志打印类
 * author by admin
 * create on 2018/5/16.
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "NetworkManager";
    private String mTag;
    private boolean mShowResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        mShowResponse = showResponse;
        mTag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, true);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    /**
     *  打印出响应的日志
     */
    private Response logForResponse(Response response) {
        try {
            //===>response log
            Log.e(mTag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(mTag, "mUrl : " + clone.request().url());
            Log.e(mTag, "code : " + clone.code());
            Log.e(mTag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                Log.e(mTag, "message : " + clone.message());

            if (mShowResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.e(mTag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Log.e(mTag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.e(mTag, "responseBody's content : " + " maybe [mFile part] , too large too print , ignored!");
                        }
                    }
                }
            }

            Log.e(mTag, "========response'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }

    /**
     *  打印出请求的日志
     */
    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.e(mTag, "========request'log=======");
            Log.e(mTag, "method : " + request.method());
            Log.e(mTag, "mUrl : " + url);
            if (headers != null && headers.size() > 0) {
                Log.e(mTag, "mHeaders : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(mTag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(mTag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(mTag, "requestBody's content : " + " maybe [mFile part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(mTag, "========request'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     *  判断是否是文本类型
     */
    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    /**
     *  将请求体转换为字符串
     */
    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
