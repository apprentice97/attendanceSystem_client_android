package com.example.attendancesystem_client_android.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.attendancesystem_client_android.R;

public class StudentFragment1 extends Fragment implements View.OnClickListener{
    private View view;

    public StudentFragment1() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_fragment_1, container, false);
        bindView();
        draw();
        return view;
    }

    private void bindView(){
    }

    private void draw(){
    }

    @Override
    public void onClick(View v) {

    }
}
