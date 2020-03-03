package com.example.attendancesystem_client_android.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.bean.CourseAttendance;
import com.example.attendancesystem_client_android.recyclerView.Decoration;
import com.example.attendancesystem_client_android.recyclerView.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeacherCourseSpecific extends AppCompatActivity  implements View.OnClickListener{
    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
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
                Toast.makeText(getApplicationContext(), "点击了第" + position + "项", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Decoration(this, OrientationHelper.VERTICAL));
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
                List<String> list = new ArrayList<>();
                for(int i = 0; i < listClass.size(); i ++){
                    list.add(listClass.get(i).toString());
                }
                recyclerAdapter.setDataString(list);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
