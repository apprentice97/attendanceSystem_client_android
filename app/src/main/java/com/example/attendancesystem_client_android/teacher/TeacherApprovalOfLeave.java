package com.example.attendancesystem_client_android.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeacherApprovalOfLeave extends AppCompatActivity  implements View.OnClickListener{

    private TextView courseId;
    private TextView serialNumber;
    private TextView studentId;
    private TextView studentName;
    private TextView reason;
    private Button reject;
    private Button approve;
    private Attendance attendance;
    private Activity mActivity;
    private TextView time;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.teacher_approval_of_leave);
        bindView();
        draw();
    }

    private void bindView(){
        courseId = findViewById(R.id.course_id);
        serialNumber = findViewById(R.id.serial_number);
        studentId = findViewById(R.id.student_id);
        studentName = findViewById(R.id.student_name);
        reason = findViewById(R.id.reason);
        reject = findViewById(R.id.reject);
        approve = findViewById(R.id.approve);
        reject.setOnClickListener(this);
        approve.setOnClickListener(this);
        time= findViewById(R.id.time);
        mActivity = this;
        webView = findViewById(R.id.webView);
    }

    private void draw(){
        attendance = GlobalVariable.getInstance().getApplication_for_leave().get(GlobalVariable.getInstance().getApplication_for_leave_position());
        courseId.setText("          课程号：" + attendance.getCourse_id());
        serialNumber.setText("          点名：第" + attendance.getSerial_number()+"次");
        studentId.setText("          学号：" + attendance.getStudent_id());
        studentName.setText("          姓名：" + attendance.getStudent_name());
        reason.setText("          原因：" + attendance.getReasonString());
        time.setText("          时间：" + attendance.getTime());
        String picture_name = attendance.getSerial_number() + attendance.getStudent_id() + attendance.getTeacher_id() + attendance.getCourse_id();
        webView.loadUrl("http://192.168.137.1/mgr/teacher/?action=request_image&picture_name=" + picture_name);
    }

    @Override
    public void onClick(View v) {
        if(v == reject){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("action", "reject_application");
                    map.put("serial_number", attendance.getSerial_number());
                    map.put("student_id", attendance.getStudent_id());
                    map.put("course_id", attendance.getCourse_id());
                    map.put("teacher_id", attendance.getTeacher_id());
                    OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                }
            }).start();
            setResult(1,new Intent());
            mActivity.finish();
        }
        else if(v == approve){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("action", "approve_application");
                    map.put("serial_number", attendance.getSerial_number());
                    map.put("student_id", attendance.getStudent_id());
                    map.put("course_id", attendance.getCourse_id());
                    map.put("teacher_id", attendance.getTeacher_id());
                    OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                }
            }).start();
            setResult(1,new Intent());
            mActivity.finish();
        }
    }
}
