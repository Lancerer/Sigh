package com.example.lancer.sigh.util;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Lancer on 2018/6/4.
 */

public class HttpUtil {
    public static void getRequest(String path, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
