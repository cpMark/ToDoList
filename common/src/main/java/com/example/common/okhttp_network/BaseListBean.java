package com.example.common.okhttp_network;

import java.util.ArrayList;

public class BaseListBean<T> {


    /**
     * code : 1
     * message : 成功
     * data : {"a":11,"b":"123"}
     */

    private int          code;
    private String       msg;
    private ArrayList<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
