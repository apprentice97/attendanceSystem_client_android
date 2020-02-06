package com.example.attendancesystem_client_android.student;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentFragment2 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView tv;

    public StudentFragment2() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_fragment_2, container, false);
        bindView();
        deal();
        return view;
    }

    private void bindView(){
        tv = view.findViewById(R.id.fg2_txt);
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
                tv.setText(string);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
