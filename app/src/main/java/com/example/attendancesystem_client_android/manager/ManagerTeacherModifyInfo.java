package com.example.attendancesystem_client_android.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManagerTeacherModifyInfo extends AppCompatActivity implements View.OnClickListener {
    private EditText teacher_id;
    private EditText teacher_name;
    private EditText teacher_password;
    private EditText teacher_email;
    private Button back;
    private Button modify;
    private int position;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.manager_teacher_modify_info);
        initView();
        draw();
    }

    private void initView(){
        teacher_id = findViewById(R.id.teacher_id);
        teacher_name = findViewById(R.id.teacher_name);
        teacher_password = findViewById(R.id.teacher_password);
        teacher_email = findViewById(R.id.teacher_email);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        modify = findViewById(R.id.modify);
        modify.setOnClickListener(this);

    }

    private void draw(){
        position = GlobalVariable.getInstance().getManager_teacher_position();
        teacher = GlobalVariable.getInstance().getManager_teacher().get(position);
        teacher_id.setText(teacher.getTeacher_id());
        teacher_name.setText(teacher.getTeacher_name());
        teacher_password.setText(teacher.getPassword());
        teacher_email.setText(teacher.getEmail());
    }

    @Override
    public void onClick(View v) {
        if(back == v){
            finish();
        }
        else if(modify == v){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("action", "reset_password");
                    map.put("user", "teacher");
                    teacher.setTeacher_name(String.valueOf(teacher_name.getText()));
                    teacher.setPassword(String.valueOf(teacher_password.getText()));
                    teacher.setEmail(String.valueOf(teacher_email.getText()));
                    GlobalVariable.getInstance().getManager_teacher().get(position).setTeacher_name(teacher.getTeacher_name());
                    GlobalVariable.getInstance().getManager_teacher().get(position).setPassword(teacher.getPassword());
                    GlobalVariable.getInstance().getManager_teacher().get(position).setEmail(teacher.getEmail());
                    map.put("teacher_id", teacher.getTeacher_id() );
                    map.put("teacher_name", teacher.getTeacher_name() );
                    map.put("teacher_password", teacher.getPassword());
                    map.put("teacher_email", teacher.getEmail());
                    OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/manager/", map);
                    assert response != null;
                    Map content = (Map) JSONObject.parse(response.content);
                    String string = Objects.requireNonNull(content.get("data")).toString();
                    finish();
                }
            }).start();
        }
    }
}
