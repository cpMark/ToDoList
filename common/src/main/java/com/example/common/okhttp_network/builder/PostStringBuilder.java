package com.example.common.okhttp_network.builder;

import com.example.common.okhttp_network.request.PostStringRequest;
import com.example.common.okhttp_network.request.RequestCall;

import okhttp3.MediaType;

/**
 * function：提交字符串Post构建器
 * author by cpMark
 * create on 2018/5/16.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String mContent;
    private MediaType mMediaType;


    public PostStringBuilder content(String content)
    {
        this.mContent = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(mUrl, mTag, mParams, mHeaders, mContent, mMediaType,mId).build();
    }

}
