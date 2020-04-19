package com.example.attendancesystem_client_android.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.recyclerView.Decoration;
import com.example.attendancesystem_client_android.recyclerView.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeacherFragment1 extends Fragment implements View.OnClickListener{
    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    public  TeacherFragment1() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindView(inflater, container);
        draw();
        return view;
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.my_recycler_view, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new MyRecyclerAdapter(getContext(), new MyRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalVariable.getInstance().setApplication_for_leave_position(position);
                startActivityForResult(new Intent(getContext(), TeacherApprovalOfLeave.class), 0);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Decoration(Objects.requireNonNull(getContext()), OrientationHelper.VERTICAL));
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "list_application_for_leave");
                map.put("teacher_id", GlobalVariable.getInstance().getAccount());
                OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                assert response != null;
                Map content = (Map) JSONObject.parse(response.content);
                String string = Objects.requireNonNull(content.get("data")).toString();
                List<Attendance> listClass = JSON.parseArray(string, Attendance.class);
                GlobalVariable.getInstance().setApplication_for_leave(listClass);
                List<String> list = new ArrayList<>(toListString(listClass));
                recyclerAdapter.setDataString(list);
            }
        }).start();
    }

    @Deprecated
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            draw();
        }
        else{
            //do nothing ...
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1 == resultCode) {
            draw();
        }
        else{
            Log.e("TeacherFragment1", "no resultCode == " + requestCode);
        }
    }

    private List<String> toListString(List<Attendance> listClass){
        List<String> ret = new ArrayList<>();
        for(int i = 0; i < listClass.size(); i ++){
            ret.add("课程:" + listClass.get(i).getCourse_id() + "    第" +  listClass.get(i).getSerial_number() + "次点名" + "    " +
                    listClass.get(i).getStudent_id() + listClass.get(i).getStudent_name());
        }
        return ret;
    }
}
