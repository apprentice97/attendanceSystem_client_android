package com.example.attendancesystem_client_android.manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.bean.Student;
import com.example.attendancesystem_client_android.bean.Teacher;
import com.example.attendancesystem_client_android.recyclerView.Decoration;
import com.example.attendancesystem_client_android.recyclerView.MyRecyclerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManagerFragment2 extends Fragment{
    private View view;
    private MyHandler handler;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean firstInstall = true;


    public ManagerFragment2() { }


    static class MyHandler extends Handler {
        WeakReference<ManagerFragment2> fragment;

        MyHandler(ManagerFragment2 upFragment) {
            fragment = new WeakReference<>(upFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            ManagerFragment2 theFragment = fragment.get();
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 0x0001:
                    System.out.println("hello world!");
                    break;
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_recycler_view, container, false);
        bindView(inflater, container);
        draw();
        return view;
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        handler = new MyHandler(this);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new MyRecyclerAdapter(getContext(), new MyRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new Decoration(Objects.requireNonNull(getContext()), OrientationHelper.VERTICAL));
        firstInstall = false;
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "list_teacher");
                OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/manager/", map);
                assert response != null;
                Map content = (Map) JSONObject.parse(response.content);
                String string = Objects.requireNonNull(content.get("data")).toString();
                List<Teacher> listClass = JSON.parseArray(string, Teacher.class);
                List<String> list = new ArrayList<>(toListString(listClass));
                recyclerAdapter.setDataString(list);
            }
        }).start();
    }

    @Deprecated
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && !firstInstall){
            draw();
        }
    }
    private List<String> toListString(List<Teacher> listClass){
        List<String> ret = new ArrayList<>();
        for(int i = 0; i < listClass.size(); i ++){
            ret.add(listClass.get(i).getTeacher_id() + "    " + listClass.get(i).getTeacher_name() + "    密码：" + listClass.get(i).getPassword());
        }
        return ret;
    }
}

