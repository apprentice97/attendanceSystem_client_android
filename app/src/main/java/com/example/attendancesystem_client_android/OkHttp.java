package com.example.attendancesystem_client_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
    public static void login(String account, String password, int type, Intent intent, Activity activity){
        Log.e("login_type", String.valueOf(type));
        String json, url;
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json, charset=utf-8");
        if(type == 1){
            json = "{\"action\": \"student_login\", \"data\": {\"account\": \"" + account + "\",\"password\":\"" + password + "\"}}";
            url = "http://192.168.137.1/mgr/student/";
        }
        else if(type == 2){
            json = "{\"action\": \"teacher_login\", \"data\": {\"account\": \"" + account + "\",\"password\":\"" + password + "\"}}";
            url = "http://192.168.137.1/mgr/teacher/";
        }
        else{
            json = "{\"action\": \"manager_login\", \"data\": {\"account\": \"" + account + "\",\"password\":\"" + password + "\"}}";
            url = "http://192.168.137.1/mgr/manager/";
        }
        Log.e("login", url + json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        assert response.body() != null;
                        String result = response.body().string();
                        JSONObject ret = null;
                        ret = new JSONObject(result);
                        if(String.valueOf(ret.get("ret")).equals("0")){
                            activity.startActivity(intent);
                        }
                        else if(String.valueOf(ret.get("ret")).equals("1")){
                            Looper.prepare();
                            Toast.makeText(activity, "账号或密码错误！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                        else{
                            Log.e("login", String.valueOf(ret.get("ret")));
                            Looper.prepare();
                            Toast.makeText(activity, "账号不存在！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
