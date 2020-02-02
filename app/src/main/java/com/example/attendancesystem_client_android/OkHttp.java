package com.example.attendancesystem_client_android;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {

    public static boolean login(String url, String account, String password, int type){
        MediaType JSON = MediaType.parse("application/json, charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = "{\"action\": \"student_login\", \"data\": {\"account\": \"" + account + "\",\"password\":\"" + password + "\"}}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://192.168.137.1/mgr/student/")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    JSONObject ret = null;
                    try {
                        ret = new JSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return false;
    }
    public static void getSyn(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //创建Request
                    Request request = new Request.Builder()
                            .url(url)//访问连接
                            .get()
                            .build();
                    //创建Call对象
                    Call call = client.newCall(request);
                    //通过execute()方法获得请求响应的Response对象
                    Response response = call.execute();

                    if (response.isSuccessful()) {
                        //处理网络请求的响应，处理UI需要在UI线程中处理
                        //...
                        String result = response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void post(){
        MediaType JSON = MediaType.parse("application/json, charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = "{\"action\": \"student_login\", \"data\": {\"account\": \"B16041735\",\"password\":\"555555\"}}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://192.168.137.1/mgr/student/")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                }
            }
        });
    }
}
