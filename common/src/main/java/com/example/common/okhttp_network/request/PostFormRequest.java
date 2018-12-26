package com.example.common.okhttp_network.request;


import com.example.common.okhttp_network.builder.PostFormBuilder;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * function：Post请求--表单格式
 * author by admin
 * create on 2018/5/16.
 */
public class PostFormRequest extends OkHttpRequest {

    private List<PostFormBuilder.FileInput> mFileInputs;

    public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id,List<PostFormBuilder.FileInput> fileInputs) {
        super(url, tag, params, headers, id);
        mFileInputs = fileInputs;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (mFileInputs == null || mFileInputs.isEmpty()) {
            //单纯的键值对，将mParams遍历添加到builder中即可
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            FormBody formBody = builder.build();
            return formBody;
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(builder);

            for (int i = 0; i < mFileInputs.size(); i++) {
                PostFormBuilder.FileInput fileInput = mFileInputs.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.mFilename)), fileInput.mFile);
                builder.addFormDataPart(fileInput.mKey, fileInput.mFilename, fileBody);
            }
            return builder.build();
        }
    }

    private void addParams(FormBody.Builder builder) {
        if (mParams != null) {
            for (String key : mParams.keySet()) {
                builder.add(key, mParams.get(key));
            }
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, mParams.get(key)));
            }
        }
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
