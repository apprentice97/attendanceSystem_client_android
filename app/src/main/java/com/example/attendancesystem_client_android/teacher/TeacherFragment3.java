package com.example.attendancesystem_client_android.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.OkHttp;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.ToastChildThread;
import com.example.attendancesystem_client_android.bean.Attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class  TeacherFragment3 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView teacherId;
    private TextView teacherName;
    private Button get_result;
    public  TeacherFragment3() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.teacher_fragment_3, container, false);
        bindView();
        draw();
        return view;
    }

    private void bindView(){
        teacherId = view.findViewById(R.id.teacher_id);
        teacherName = view.findViewById(R.id.teacher_name);
        get_result = view.findViewById(R.id.get_result);
    }

    private void draw(){
        teacherId.setText("             工号：     " + GlobalVariable.getInstance().getAccount());
        teacherName.setText("             姓名：     " + GlobalVariable.getInstance().getTeacher_name());
        get_result.setOnClickListener(this);
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
        if(get_result == v){
            //发送请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("action", "teacher_request_statistics");
                    map.put("teacher_email", GlobalVariable.getInstance().getTeacher_email());
                    map.put("teacher_id", GlobalVariable.getInstance().getAccount());
                    OkHttp.Response response = OkHttp.httpGetForm("http://192.168.137.1/mgr/teacher/", map);
                    ToastChildThread.show(getContext(), "成功获取考勤统计数据，请注意查收！", Toast.LENGTH_SHORT);
                }
            }).start();
        }
    }
}
