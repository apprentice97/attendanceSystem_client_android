package com.example.attendancesystem_client_android.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeacherModifyPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText student_id;
    private EditText student_name;
    private EditText password_1;
    private EditText password_2;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.teacher_modify_password);
        bindView();
        draw();
    }

    private void bindView(){
        student_id = findViewById(R.id.student_id);
        student_name = findViewById(R.id.student_name);
        password_1 = findViewById(R.id.password_1);
        password_2 = findViewById(R.id.password_2);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    private void draw(){
        String string_teacher_id = GlobalVariable.getInstance().getAccount();
        String string_teacher_name = GlobalVariable.getInstance().getTeacher_name();
        student_id.setText(string_teacher_id);
        student_name.setText(string_teacher_name);
    }

    @Override
    public void onClick(View v) {
        if(v == submit){
            String string_password_1 = String.valueOf(password_1.getText());
            String string_password_2 = String.valueOf(password_2.getText());
            if(string_password_1.equals(string_password_2) && !string_password_1.equals("123456")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("action", "teacher_reset_password");
                        map.put("teacher_id", GlobalVariable.getInstance().getAccount());
                        map.put("password", string_password_1);
                        OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                        finish();
                    }
                }).start();
            }
            else if(string_password_1.equals("123456")){
                Toast.makeText(getBaseContext(), "不能设置为初始密码！", Toast.LENGTH_SHORT).show();
                password_1.setText("");
                password_2.setText("");
            }
            else{
                Toast.makeText(getBaseContext(), "两次输入的密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                password_1.setText("");
                password_2.setText("");
            }
        }
    }
}
