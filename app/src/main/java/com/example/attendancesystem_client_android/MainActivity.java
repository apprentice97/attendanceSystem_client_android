package com.example.attendancesystem_client_android;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.manager.ManagerMain;
import com.example.attendancesystem_client_android.student.StudentMain;
import com.example.attendancesystem_client_android.teacher.TeacherCourse;
import com.example.attendancesystem_client_android.teacher.TeacherMain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        //startActivity(new Intent(this, TeacherCourse.class));
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

    @Override
    public void onClick(View view) {
        if(view == btn_login){
            login();
        }
        else if(view == tv_register){
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClass(MainActivity.this, Register.class);
            //startActivity(intent);
        }
    }

    void login(){
        boolean internet = false;
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext() .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                internet = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                internet = true;
            }
        }
        if(internet){
            GlobalVariable.getInstance().setAccount(String.valueOf(et_account.getText()));
            GlobalVariable.getInstance().setPassword(String.valueOf(et_password.getText()));
            GlobalVariable.getInstance().setType(getType());
            String account =  GlobalVariable.getInstance().getAccount();
            String password =  GlobalVariable.getInstance().getPassword();
            int type = GlobalVariable.getInstance().getType();
            Intent intent = new Intent("android.intent.action.MAIN");
            if(type == 1){
                intent.setClass(MainActivity.this, StudentMain.class);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("action", "student_login");
                        map.put("account", account);
                        map.put("password", password);
                        OkHttp.Response response = OkHttp.httpPostForm("http://192.168.137.1/mgr/student/", map);
                        assert response != null;
                        Map content = (Map) JSONObject.parse(response.content);
                        String code = Objects.requireNonNull(content.get("ret")).toString();
                        Log.e("MainActivity", response.content);
                        if(code.equals("0")){
                            map = new HashMap<String, String>();
                            map.put("action", "student_get_self_info");
                            map.put("student_id", account);
                            response = OkHttp.httpPostForm("http://192.168.137.1/mgr/student/", map);
                            assert response != null;
                            content = (Map) JSONObject.parse(response.content);
                            String student_name = Objects.requireNonNull(content.get("student_name")).toString();
                            String student_class_id = Objects.requireNonNull(content.get("student_class_id")).toString();
                            GlobalVariable.getInstance().setStudent_name(student_name);
                            GlobalVariable.getInstance().setStudent_class_id(student_class_id);

                            startActivity(intent);
                        }
                        else if(code.equals("1")){
                            ToastChildThread.show(MainActivity.this, "账号或密码错误！", Toast.LENGTH_LONG);
                        }
                        else if(code.equals("2")){
                            ToastChildThread.show(MainActivity.this, "账号不存在！", Toast.LENGTH_LONG);
                        }
                    }
                }).start();
            }
            else if(type == 2){
                intent.setClass(MainActivity.this, TeacherMain.class);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("action", "teacher_login");
                        map.put("account", account);
                        map.put("password", password);
                        OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                        Map content = (Map) JSONObject.parse(response.content);
                        String code = Objects.requireNonNull(content.get("ret")).toString();
                        if(code.equals("0")){
                            startActivity(intent);
                        }
                        else if(code.equals("1")){
                            ToastChildThread.show(MainActivity.this, "账号或密码错误！", Toast.LENGTH_LONG);
                        }
                        else if(code.equals("2")){
                            ToastChildThread.show(MainActivity.this, "账号不存在！", Toast.LENGTH_LONG);
                        }
                    }
                }).start();
            }
            else if(type == 3){
                intent.setClass(MainActivity.this, ManagerMain.class);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("action", "manager_login");
                        map.put("account", account);
                        map.put("password", password);
                        OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/manager/", map);
                        Map content = (Map) JSONObject.parse(response.content);
                        String code = Objects.requireNonNull(content.get("ret")).toString();
                        if(code.equals("0")){
                            startActivity(intent);
                        }
                        else if(code.equals("1")){
                            ToastChildThread.show(MainActivity.this, "账号或密码错误！", Toast.LENGTH_LONG);
                        }
                        else if(code.equals("2")){
                            ToastChildThread.show(MainActivity.this, "账号不存在！", Toast.LENGTH_LONG);
                        }
                    }
                }).start();
            }
        }
        else{
            ToastChildThread.show(MainActivity.this, "请检查网络连接！", Toast.LENGTH_LONG);
        }
    }

    int getType(){
        if(rb_student.isChecked()) return 1;
        else if(rb_teacher.isChecked()) return 2;
        else return 3;
    }
}
