package com.example.attendancesystem_client_android;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUpload{

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    private static final OkHttpClient client = new OkHttpClient();
    public static void run(File f) throws Exception {
        final File file=f;
        new Thread() {
            @Override
            public void run() {
                //子线程需要做的工作
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("action","list_course")
                        .addFormDataPart("account","B16041735")
                        .addFormDataPart("password","123456")
                        .build();
                //设置为自己的ip地址
                //http://192.168.137.1/
                Request request = new Request.Builder()
                        .url("http://192.168.137.1/mgr/student/")
                        .post(requestBody)
                        .build();
                try(Response response = client.newCall(request).execute()){
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
