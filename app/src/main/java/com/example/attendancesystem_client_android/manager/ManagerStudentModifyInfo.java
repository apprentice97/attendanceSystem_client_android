package com.example.attendancesystem_client_android.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManagerStudentModifyInfo extends AppCompatActivity implements View.OnClickListener {
    private EditText student_id;
    private EditText student_name;
    private EditText student_class_id;
    private EditText student_password;
    private Button back;
    private Button modify;
    private int position;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.manager_student_modify_info);
        initView();
        draw();
    }

    private void initView(){
        student_id = findViewById(R.id.student_id);
        student_name = findViewById(R.id.student_name);
        student_password = findViewById(R.id.student_password);
        student_class_id = findViewById(R.id.student_class_id);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        modify = findViewById(R.id.modify);
        modify.setOnClickListener(this);

    }

    private void draw(){
        position = GlobalVariable.getInstance().getManager_student_position();
        student = GlobalVariable.getInstance().getManager_student().get(position);
        student_id.setText(student.getStudent_id());
        student_name.setText(student.getStudent_name());
        student_class_id.setText(student.getClass_id());
        student_password.setText(student.getPassword());
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
                    map.put("user", "student");
                    student.setStudent_name(String.valueOf(student_name.getText()));
                    student.setClass_id(String.valueOf(student_class_id.getText()));
                    student.setPassword(String.valueOf(student_password.getText()));
                    GlobalVariable.getInstance().getManager_student().get(position).setStudent_name(student.getStudent_name());
                    GlobalVariable.getInstance().getManager_student().get(position).setClass_id(student.getClass_id());
                    GlobalVariable.getInstance().getManager_student().get(position).setPassword(student.getPassword());
                    map.put("student_id", student.getStudent_id() );
                    map.put("student_name", student.getStudent_name() );
                    map.put("student_class_id", student.getClass_id());
                    map.put("student_password", student.getPassword());
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
