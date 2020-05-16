package com.example.attendancesystem_client_android.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.recyclerView.Decoration;
import com.example.attendancesystem_client_android.recyclerView.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudentCourseAttendance extends AppCompatActivity implements View.OnClickListener{
    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.teacher_course);
        bindView();
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
                GlobalVariable.getInstance().setStudent_attendance_position(position);
                startActivity(new Intent(StudentCourseAttendance.this, StudentRequestForLeave.class));
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
                map.put("action", "course_attendance");
                map.put("student_id", GlobalVariable.getInstance().getAccount());
                map.put("course_id",GlobalVariable.getInstance().getCourse().get(GlobalVariable.getInstance().getStudent_course_id()).getCourse_id());
                OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/student/", map);
                assert response != null;
                Map content = (Map) JSONObject.parse(response.content);
                String string = Objects.requireNonNull(content.get("data")).toString();
                List<Attendance> listClass = JSON.parseArray(string, Attendance.class);
                GlobalVariable.getInstance().setStudent_attendance(listClass);
                List<String> list = new ArrayList<>(toListString(listClass));
                recyclerAdapter.setDataString(list);
            }
        }).start();
    }

    @Override
    public void onResume(){
        super.onResume();
        draw();
    }

    @Override
    public void onClick(View v) {

    }

    private List<String> toListString(List<Attendance> listClass){
        List<String> ret = new ArrayList<>();
        for(int i = 0; i < listClass.size(); i ++){
            String add = "";
            if(listClass.get(i).getModify().equals("0") || listClass.get(i).getModify().equals("2")){
                add = "缺勤";
            }
            else if(listClass.get(i).getType().equals("0")){
                add = "出勤";
            }
            else if(listClass.get(i).getType().equals("1")){
                add = "病假";
            }
            else if(listClass.get(i).getType().equals("2")){
                add = "事假";
            }
            else if(listClass.get(i).getType().equals("3")){
                add = "缺勤";
            }
            ret.add(listClass.get(i).getTime() + "  课程号:" + listClass.get(i).getCourse_id() + "  第" +  listClass.get(i).getSerial_number() + "次点名  " + add);
        }
        return ret;
    }
}
