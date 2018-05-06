package com.liwen.dor.util;

import org.xutils.HttpManager;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by ldn on 2018/5/4.
 */

public class HttpClient {
    private static String url = "http://192.168.0.107:8888/api";
    private OkHttpClient client;
    private static HttpClient httpClient;

    public static HttpClient newInit() {
        if (null == httpClient) {
            httpClient = new HttpClient();
            httpClient.client = new OkHttpClient
                    .Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return httpClient;
    }


    public Call aaa(String x, String y) {
//            String path = "/app/loginTel";
        String path = "/getDisplay";
        Request request = new Request.Builder().get()
                .url(url + path + "?x=" + x + "&y=" + y)
                .build();
        return client.newCall(request);
    }

    public Call getAllDisplay() {
//            String path = "/app/loginTel";
        String path = "/getDisplay";
        Request request = new Request.Builder().get()
                .url(url + path)
                .build();
        return client.newCall(request);
    }

    public Call login(String name, String pwd) {
//            String path = "/app/loginTel";
        String path = "/login";
        Request request = new Request.Builder().get()
                .url(String.format("%s%s?u=%s&pwd=%s", url, path, name, pwd))
                .build();
        return client.newCall(request);
    }


}
