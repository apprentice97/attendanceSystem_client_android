package com.example.attendancesystem_client_android.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeacherModifyAttendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private TextView courseId;
    private TextView serialNumber;
    private TextView studentId;
    private TextView studentName;
    private Spinner reason;
    private ArrayAdapter<String> reason_adapter;
    private Button modify;
    private Button cancel;
    private Attendance attendance;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_modify_attendance);
        Objects.requireNonNull(getSupportActionBar()).hide();
        bindView();
        draw();
    }

    private void bindView(){
        courseId = findViewById(R.id.course_id);
        serialNumber = findViewById(R.id.serial_number);
        studentId = findViewById(R.id.student_id);
        studentName = findViewById(R.id.student_name);
        reason = findViewById(R.id.reason);
        modify = findViewById(R.id.reject);
        cancel = findViewById(R.id.approve);
        modify.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mActivity = this;

        ArrayList<String> list = new ArrayList<>();
        list.add("出勤");
        list.add("病假");
        list.add("事假");
        list.add("缺勤");
        reason = findViewById(R.id.reason);
        reason_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason.setAdapter( reason_adapter);
        reason.setOnItemSelectedListener(this);
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                attendance = GlobalVariable.getInstance().getTeacher_check().get(GlobalVariable.getInstance().getTeacher_check_position());
                courseId.setText("          课程号：" + attendance.getCourse_id());
                serialNumber.setText("          点名：第" + attendance.getSerial_number()+"次");
                studentId.setText("          学号：" + attendance.getStudent_id());
                studentName.setText("          姓名：" + attendance.getStudent_name());
                reason.setSelection(Integer.valueOf(attendance.getType()));
            }
        }){

        }.start();
    }

    @Override
    public void onClick(View v) {
        if(v == modify){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("action", "teacher_modify_attendance");
                    map.put("serial_number", attendance.getSerial_number());
                    map.put("student_id", attendance.getStudent_id());
                    map.put("course_id", attendance.getCourse_id());
                    map.put("teacher_id", attendance.getTeacher_id());
                    map.put("type", attendance.getType());
                    OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                    assert response != null;
                    Map content = (Map) JSONObject.parse(response.content);
                    String string = Objects.requireNonNull(content.get("data")).toString();
                    setResult(1,new Intent());
                    mActivity.finish();
                }
            }).start();
        }
        else{
            setResult(1,new Intent());
            mActivity.finish();
        }

    }


    // todo 修改attendance的type 并且正确显示最初的type

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        attendance.setType(Integer.toString(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
