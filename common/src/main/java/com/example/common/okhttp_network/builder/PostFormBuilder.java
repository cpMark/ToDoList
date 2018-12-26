package com.example.common.okhttp_network.builder;

import com.example.common.okhttp_network.request.PostFormRequest;
import com.example.common.okhttp_network.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * function：Post提交表单构建者
 * author by admin
 * create on 2018/5/16.
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParams {

    private List<FileInput> mFileInputs = new ArrayList<>();

    @Override
    public RequestCall build() {
        return new PostFormRequest(mUrl, mTag, mParams, mHeaders, mId,mFileInputs).build();
    }

    public PostFormBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.mFileInputs.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        mFileInputs.add(new FileInput(name, filename, file));
        return this;
    }

    /**
     *  文件信息的简单封装类
     */
    public static class FileInput {
        public String mKey;
        public String mFilename;
        public File mFile;

        public FileInput(String name, String filename, File file) {
            this.mKey = name;
            this.mFilename = filename;
            this.mFile = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "mKey='" + mKey + '\'' +
                    ", mFilename='" + mFilename + '\'' +
                    ", mFile=" + mFile +
                    '}';
        }
    }


    @Override
    public PostFormBuilder params(Map<String, String> params) {
        mParams = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return this;
    }
}
