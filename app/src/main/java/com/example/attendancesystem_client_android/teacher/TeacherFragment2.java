package com.example.attendancesystem_client_android.teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.ToastChildThread;
import com.example.attendancesystem_client_android.bean.Course;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class  TeacherFragment2 extends Fragment implements View.OnClickListener{
    private View view;
    private RecyclerView recyclerView;
    private TeacherRecyclerAdapter2 recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean firstInstall = true;

    public  TeacherFragment2() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindView(inflater, container);
        draw();
        return view;
    }


    @SuppressLint("WrongConstant")
    private void bindView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.teacher_fragment_2, container, false);
        recyclerView = view.findViewById(R.id.t_f2_recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new TeacherRecyclerAdapter2(getActivity(), new TeacherRecyclerAdapter2.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                GlobalVariable.getInstance().setTeacher_course_id(position);
                startActivity(new Intent(getActivity(), TeacherCourse.class));
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new TeacherDecoration2(view.getContext(),OrientationHelper.VERTICAL));
        firstInstall = false;
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "list_course");
                map.put("account", GlobalVariable.getInstance().getAccount());
                OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                assert response != null;
                Map content = (Map) JSONObject.parse(response.content);
                String string = Objects.requireNonNull(content.get("data")).toString();
                List<Course> courseList = JSON.parseArray(string, Course.class);
                GlobalVariable.getInstance().setCourse(courseList);
                recyclerViewAdapter.setData(courseList);

                //将更新UI的操作放在runOnUiThread()....
//                Objects.requireNonNull(getActivity()).runOnUiThread((new Runnable() {
//                    public void run()
//                    {
//                        recyclerViewAdapter.notifyDataSetChanged();
//                    }
//                }));
            }
        }).start();
    }

    @Deprecated
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && !firstInstall){
            draw();
        }
        else{
            //do nothing ...
        }
    }

    @Override
    public void onClick(View v) {
    }
}
