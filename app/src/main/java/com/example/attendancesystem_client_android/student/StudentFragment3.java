package com.example.attendancesystem_client_android.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.attendancesystem_client_android.CircleImageView;
import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.R;

import org.w3c.dom.Text;

public class StudentFragment3 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView studentId;
    private TextView studentName;
    private TextView studentClassId;

    public StudentFragment3() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_fragment_3, container, false);
        final ImageView imageView = (ImageView)view.findViewById(R.id.portrait);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "可见不是一个方框", Toast.LENGTH_SHORT).show();
            }
        });
        bindView();
        draw();
        return view;
    }

    private void bindView(){
        studentId = view.findViewById(R.id.student_id);
        studentName = view.findViewById(R.id.student_name);
        studentClassId = view.findViewById(R.id.student_class_id);
    }

    private void draw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                studentId.setText(GlobalVariable.getInstance().getAccount());
                studentName.setText(GlobalVariable.getInstance().getStudent_name());
                studentClassId.setText(GlobalVariable.getInstance().getStudent_class_id());
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
