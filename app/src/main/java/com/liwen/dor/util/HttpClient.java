package com.liwen.dor.util;

import com.liwen.dor.constant.SPConstant;
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
        String ip_text = SpUtil.getSP(SPConstant.IP_TEXT, String.class);//读取 本地保存的ip地址
        if (!"null".equals(ip_text)) {
            url = ip_text;
        }
        return httpClient;
    }



    public Call getAllDisplay() {
        String path = "/getDisplay";
        Request request = new Request.Builder().get()
                .url(url + path)
                .build();
        return client.newCall(request);
    }

    public Call getAllSource(){
        Request request = new Request.Builder().get()
                .url(url + "/getAllSource")
                .build();
        return client.newCall(request);
    }

    public Call login(String name, String pwd) {
        String path = "/login";
        Request request = new Request.Builder().get()
                .url(String.format("%s%s?u=%s&pwd=%s", url, path, name, pwd))
                .build();
        return client.newCall(request);
    }

    public Call getMultiScreenState(){
        Request request = new Request.Builder().get()
                .url(url + "/getMultiState")
                .build();
        return client.newCall(request);
    }


}
