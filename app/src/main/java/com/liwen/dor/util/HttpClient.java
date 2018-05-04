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
    private static String url="http://www.baidu.com";
    private OkHttpClient client;
    private static HttpClient httpClient;
    public static HttpClient newInit(){
        if (null==httpClient){
            httpClient=new HttpClient();
            httpClient.client = new OkHttpClient
                    .Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return httpClient;
    }



        public Call aaa(String x, String y){
//            String path = "/app/loginTel";
            String path = "";
            Request request=new Request.Builder().get()
                    .url(url+path+"?x="+x+"&y="+y)
                    .build();
            return client.newCall(request);
        }

}
