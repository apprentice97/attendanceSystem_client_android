package com.example.attendancesystem_client_android.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.R;

public class  TeacherFragment3 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView teacherId;
    private TextView teacherName;
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
    }

    private void draw(){
        teacherId.setText("             工号：     " + GlobalVariable.getInstance().getAccount());
        teacherName.setText("             姓名：     " + GlobalVariable.getInstance().getTeacher_name());
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
}
