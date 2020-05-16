package com.example.attendancesystem_client_android.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.recyclerView.Decoration;
import com.example.attendancesystem_client_android.recyclerView.MyRecyclerAdapter;
import com.example.attendancesystem_client_android.student.StudentSignIn;

import org.w3c.dom.Text;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.security.AccessController.getContext;

public class TeacherCourseSpecific extends AppCompatActivity{
    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView my_action_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.teacher_course_specific);
        bindView();
        draw();
    }

    private void bindView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new MyRecyclerAdapter(this, new MyRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalVariable.getInstance().setTeacher_check_position(position);
                Intent intent = new Intent(TeacherCourseSpecific.this, TeacherModifyAttendance.class);
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Decoration(this, OrientationHelper.VERTICAL));
        my_action_bar = findViewById(R.id.my_action_bar);
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "course_attendance_information");
                map.put("teacher_id", GlobalVariable.getInstance().getAccount());
                map.put("course_id",GlobalVariable.getInstance().getCourse().get(GlobalVariable.getInstance().getTeacher_course_id()).getCourse_id());
                map.put("serial_number", GlobalVariable.getInstance().getTeacher_course_serial() + "");
                OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                assert response != null;
                Map content = (Map) JSONObject.parse(response.content);
                String string = Objects.requireNonNull(content.get("data")).toString();
                List<Attendance> listClass = JSON.parseArray(string, Attendance.class);
                GlobalVariable.getInstance().setTeacher_check(listClass);
                List<String> list = new ArrayList<>();
                for(int i = 0; i < listClass.size(); i ++){
                    list.add(listClass.get(i).toStudentInformation());
                }
                recyclerAdapter.setDataString(list);
                Attendance attendance = listClass.get(0);
                my_action_bar.setText( attendance.getCourse_id() + attendance.getCourse_name() + " 第" + attendance.getSerial_number() + "次点名");
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1 == resultCode) {
            draw();
        }
        else{
            Log.e("StudentFragment1", "no resultCode == " + requestCode);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //draw();
    }
}
