package com.example.attendancesystem_client_android;

import android.os.Environment;
import android.util.Log;
import android.view.textclassifier.TextLinks;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class Question {
    public static void main(String[] args){
        try {
            URL url = new URL("C:\\Users\\19093\\Desktop\001.jpg");
            Picture.compressScale(url,"C:\\Users\\19093\\Desktop\\newImage.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
