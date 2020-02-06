package com.example.attendancesystem_client_android.teacher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class  TeacherFragment2 extends Fragment implements View.OnClickListener{
    private View view;
    private MyHandler handler;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    public  TeacherFragment2() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindView(inflater, container);
        draw();
        return view;
    }

    static class MyHandler extends Handler {
        //注意下面的“PopupActivity”类是MyHandler类所在的外部类，即所在的activity
        WeakReference<TeacherFragment2> fragment;

        MyHandler(TeacherFragment2 upFragment) {
            fragment = new WeakReference<>(upFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            TeacherFragment2 theFragment = fragment.get();
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 0x0001:
                    System.out.println(msg.obj);
                case 0x0002:
                    System.out.println("hello world");
                    break;
            }
        }
    }

    @SuppressLint("WrongConstant")
    private void bindView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.teacher_fragment_2, container, false);
        handler = new MyHandler(this);
        recyclerView = view.findViewById(R.id.t_f2_recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerViewAdapter = new TeacherRecyclerAdapter2(view.getContext());
        recyclerViewAdapter = new TeacherRecyclerAdapter2(view.getContext(), new TeacherRecyclerAdapter2.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Toast.makeText(view.getContext(), "点击了第"+position+"项", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new TeacherDecoration2(view.getContext(),OrientationHelper.VERTICAL));
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
                Message message = new Message();
                message.what = 0x0001;
                message.obj = string;
                handler.sendMessage(message);

            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
