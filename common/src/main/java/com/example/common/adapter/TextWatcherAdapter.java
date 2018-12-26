package com.example.common.adapter;

import android.text.Editable;
import android.text.TextWatcher;

/**
* 文本编辑器内容变化监听器适配器
*/
public class TextWatcherAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
