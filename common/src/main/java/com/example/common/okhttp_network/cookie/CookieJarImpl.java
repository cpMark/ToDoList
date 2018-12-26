package com.example.common.okhttp_network.cookie;


import com.example.common.okhttp_network.cookie.store.CookieStore;
import com.example.common.okhttp_network.utils.ExceptionsUtils;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * function：CookieJar实现类
 * author by admin
 * create on 2018/5/16.
 */
public class CookieJarImpl implements CookieJar {

    private CookieStore mCookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            ExceptionsUtils.illegalArgument("mCookieStore can not be null.");
        }
        this.mCookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mCookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return mCookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return mCookieStore;
    }
}
