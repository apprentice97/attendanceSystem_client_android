package com.example.attendancesystem_client_android.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.R;

import org.w3c.dom.Text;

public class StudentFragment3 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView studentName;

    public StudentFragment3() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_fragment_3, container, false);
        bindView();
        draw();
        return view;
    }

    private void bindView(){
        studentName = view.findViewById(R.id.studentName);
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                studentName.setText(GlobalVariable.getInstance().getAccount());
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
