package com.example.attendancesystem_client_android;

import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;

public class OkHttp {
    public static class Response {
        public Integer code;
        public String content;
    }

    /**
     * http get 请求
     * @param url   请求uri
     * @return      Response请求结果实例，通过response.code==200判断是否有效
     */
    public static Response httpGet(String url) {

        Response response = new Response();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        proceedRequest(client, request, response);

        return response;
    }

    public static Response httpGetForm(String url, Map<String, String> para) {
        if (para == null || para.size() == 0)
            return null;

        Response response = new Response();

        OkHttpClient client = new OkHttpClient();

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

        for (Map.Entry<String, String> entry : para.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
        }

        reqBuild.url(urlBuilder.build());

        Request request = reqBuild.build();

        proceedRequest(client, request, response);

        return response;
    }

    private static void proceedRequest(OkHttpClient client, Request request, Response response) {
        try {
            okhttp3.Response temp = client.newCall(request).execute();
            response.code = temp.code();
            ResponseBody body = temp.body();
            if (temp.isSuccessful()) {
                //call string auto close body
                response.content = body.string();
            } else {
                response.content = "网络请求失败";
                temp.body().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, e.getMessage() == null ? " " : e.getMessage());
            response.code = -1;
            response.content = e.getMessage();
        }
    }

    /**
     * http post 请求
     * @param url       请求url
     * @param jsonStr    post参数
     * @return          Response请求结果实例，通过response.code==200判断
     */
    public static Response httpPost(String url, String jsonStr) {
        Response response = new Response();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        proceedRequest(client, request, response);

        return response;
    }

    /**
     * http post 请求
     * @param url       请求url
     * @param para      post参数，表单键值对
     * @return          HttpResponse请求结果实例
     */
    public static Response httpPostForm(String url, Map<String, String> para) {
        if (para == null || para.size() == 0)
            return null;

        Response response = new Response();

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();


        for (Map.Entry<String, String> entry : para.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        proceedRequest(client, request, response);

        return response;
    }

    /**
     * http put 请求
     * @param url       请求url
     * @param jsonStr    post参数
     * @return          Response请求结果实例，通过response.code==200判断
     */
    public static Response httpPut(String url, String jsonStr) {
        Response response = new Response();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        proceedRequest(client, request, response);

        return response;
    }
}



