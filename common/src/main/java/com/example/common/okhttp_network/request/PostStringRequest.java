package com.example.common.okhttp_network.request;

import com.example.common.okhttp_network.utils.ExceptionsUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * function：提交字符串Post请求
 * author by cpMark
 * create on 2018/5/16.
 */
public class PostStringRequest extends OkHttpRequest
{
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private String mContent;
    private MediaType mMediaType;


    public PostStringRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, String content, MediaType mediaType, int id)
    {
        super(url, tag, params, headers,id);
        this.mContent = content;
        this.mMediaType = mediaType;

        if (this.mContent == null)
        {
            ExceptionsUtils.illegalArgument("the mContent can not be null !");
        }
        if (this.mMediaType == null)
        {
            this.mMediaType = MEDIA_TYPE_PLAIN;
        }

    }

    @Override
    protected RequestBody buildRequestBody()
    {
        return RequestBody.create(mMediaType, mContent);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return mBuilder.post(requestBody).build();
    }
}
