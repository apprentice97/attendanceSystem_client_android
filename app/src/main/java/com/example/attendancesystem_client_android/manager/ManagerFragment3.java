package com.example.attendancesystem_client_android.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.R;

public class ManagerFragment3 extends Fragment implements View.OnClickListener{
    private View view;
    private TextView managerId;

    public  ManagerFragment3() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.manager_fragment_3, container, false);
        bindView();
        draw();
        return view;
    }

    private void bindView(){
        managerId = view.findViewById(R.id.manager_id);
    }

    private void draw(){
        managerId.setText("             账号：     " + GlobalVariable.getInstance().getAccount());
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

