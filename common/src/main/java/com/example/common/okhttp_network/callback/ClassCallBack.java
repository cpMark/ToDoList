package com.example.common.okhttp_network.callback;

import com.example.common.R;
import com.example.common.application.ContextHolder;
import com.example.common.utils.ToastUtils;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Response;

/**
 * function：字符串回调,泛型为Bean实体类
 * author by admin
 * create on 2018/5/16.
 */
public abstract class ClassCallBack<T> extends Callback<T> {

    private final Gson mGson;

    public ClassCallBack() {
        mGson = new Gson();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        return mGson.fromJson(response.body().string(), ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @Override
    public void onResponse(T response, int id) {
        if (response == null) {
            handleError(ContextHolder.getContext().getString(R.string.response_data_is_null));
            return;
        }

        success(response);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        //默认的处理方式是打印出错误。如果想自己处理，可以重写handleError
        handleError(e.getMessage());
    }

    /**
     * 失败的默认实现方式是弹吐司提示
     */
    public void handleError(String errorMessage) {
        ToastUtils.show(errorMessage);
    }



    /**
    * 成功回调
    */
    public abstract void success(T response);
}

