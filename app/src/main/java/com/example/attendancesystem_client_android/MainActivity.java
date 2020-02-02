package com.example.attendancesystem_client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancesystem_client_android.student.StudentMain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_account;
    EditText et_password;
    Button btn_login;
    TextView tv_register;
    RadioButton rb_student;
    RadioButton rb_teacher;
    RadioButton rb_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView(){
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        rb_student = findViewById(R.id.rbStudent);
        rb_teacher = findViewById(R.id.rbTeacher);
        rb_manager = findViewById(R.id.rbManager);
    }

    private void login(String url, String account, String password){
        MediaType JSON = MediaType.parse("application/json, charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = "{\"action\": \"student_login\", \"data\": {\"account\": \"" + account + "\",\"password\":\"" + password + "\"}}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
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
                        if(String.valueOf(ret.get("ret")).equals("0")){
                            Intent intent = new Intent("android.intent.action.MAIN");
                            intent.setClass(MainActivity.this, StudentMain.class);
                            startActivity(intent);
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "账号或密码错误！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btn_login){
            boolean internet = false;
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext() .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    internet = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    internet = true;
                }
            } else {
                internet = false;
            }
            if(internet){
                GlobalVariable.getInstance().setAccount(String.valueOf(et_account.getText()));
                GlobalVariable.getInstance().setPassword(String.valueOf(et_password.getText()));
                String account =  GlobalVariable.getInstance().getAccount();
                String password =  GlobalVariable.getInstance().getPassword();
                if(rb_student.isChecked()){
                    GlobalVariable.getInstance().setType(1);
                    login("http://192.168.137.1/mgr/student/", account, password);
                }
                else if(rb_teacher.isChecked()){
                    GlobalVariable.getInstance().setType(2);
                    login("http://192.168.137.1/mgr/teacher/", account, password);
                }
                else if(rb_manager.isChecked()){
                    GlobalVariable.getInstance().setType(3);
                    login("http://192.168.137.1/mgr/manager/", account, password);
                }
            }
            else{
                Toast.makeText(MainActivity.this, "请检查网络连接！", Toast.LENGTH_LONG).show();
            }
        }
        else if(view == tv_register){
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClass(MainActivity.this, Register.class);
            startActivity(intent);
        }
    }
}
