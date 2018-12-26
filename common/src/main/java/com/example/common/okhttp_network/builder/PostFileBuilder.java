package com.example.common.okhttp_network.builder;

import com.example.common.okhttp_network.request.PostFileRequest;
import com.example.common.okhttp_network.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * function：上传文件Post请求构建器
 * author by cpMark
 * create on 2018/5/16.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {


    private File mFile;
    private MediaType mMediaType;


    public OkHttpRequestBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(mUrl, mTag, mParams, mHeaders, mFile, mMediaType, mId).build();
    }

}
