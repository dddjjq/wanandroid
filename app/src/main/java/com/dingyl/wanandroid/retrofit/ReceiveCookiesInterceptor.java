package com.dingyl.wanandroid.retrofit;

import com.dingyl.wanandroid.util.MyApplication;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceiveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SharedPreferenceUtil.getInstance(MyApplication.getInstance()).receiveCookies(cookies);
        }

        return originalResponse;
    }
}
