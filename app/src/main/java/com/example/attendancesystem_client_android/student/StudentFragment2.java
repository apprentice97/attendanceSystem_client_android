package com.example.attendancesystem_client_android.student;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentFragment2 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView tv;
    private MyHandler handler;

    public StudentFragment2() { }


    static class MyHandler extends Handler {
        //注意下面的“PopupActivity”类是MyHandler类所在的外部类，即所在的activity
        WeakReference<StudentFragment2> fragment;

        MyHandler(StudentFragment2 upFragment) {
            fragment = new WeakReference<>(upFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            StudentFragment2 theFragment = fragment.get();
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 0x0001:
                    theFragment.tv.setText(msg.obj.toString());
                case 0x0002:
                    System.out.println("hello world");
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_fragment_2, container, false);
        bindView();
        deal();
        return view;
    }

    private void bindView(){
        tv = view.findViewById(R.id.fg2_txt);
        handler = new MyHandler(this);
    }

    private void deal(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "list_course");
                map.put("account", GlobalVariable.getInstance().getAccount());
                OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/student/", map);
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
};





