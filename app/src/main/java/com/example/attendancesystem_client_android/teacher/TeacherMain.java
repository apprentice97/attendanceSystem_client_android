package com.example.attendancesystem_client_android.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.attendancesystem_client_android.GlobalVariable;
import com.example.attendancesystem_client_android.R;
import com.example.attendancesystem_client_android.teacher.TeacherFragment1;
import com.example.attendancesystem_client_android.teacher.TeacherFragment2;
import com.example.attendancesystem_client_android.teacher.TeacherFragment3;
import com.example.attendancesystem_client_android.teacher.TeacherFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherMain extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb0;
    private RadioButton rb1;
    private RadioButton rb2;
    private ViewPager vPager;
    private TextView myActionBar;
    private Boolean firstInstallActivity;

    private TeacherFragmentPagerAdapter mAdapter;
    private List<Fragment> fragments;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firstInstallActivity = true;
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.teacher_main);
        TeacherFragment1 f1 = new TeacherFragment1();
        TeacherFragment2 f2 = new TeacherFragment2();
        TeacherFragment3 f3 = new TeacherFragment3();
        fragments = new ArrayList<>();
        bindViews();
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        mAdapter = new TeacherFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        vPager = findViewById(R.id.vPager);
        vPager.setAdapter(mAdapter);
        vPager.addOnPageChangeListener(this);
        rb1.setChecked(true);
        vPager.setCurrentItem(1);
        if(firstInstallActivity && GlobalVariable.getInstance().getPassword().equals("123456")){
            firstInstallActivity = false;
            startActivity(new Intent(TeacherMain.this, TeacherModifyPassword.class));
        }
    }

    private void bindViews() {
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        rb0 =  findViewById(R.id.rb0);
        rb1 = findViewById(R.id.rb1);
        rb2 =  findViewById(R.id.rb2);
        myActionBar = findViewById(R.id.my_action_bar);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb0:
                vPager.setCurrentItem(PAGE_ONE);
                myActionBar.setText("假 条");
                break;
            case R.id.rb1:
                vPager.setCurrentItem(PAGE_TWO);
                myActionBar.setText("课  程");
                break;
            case R.id.rb2:
                vPager.setCurrentItem(PAGE_THREE);
                myActionBar.setText("我  的");
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vPager.getCurrentItem()) {
                case PAGE_ONE:
                    rb0.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb1.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb2.setChecked(true);
                    break;
            }
        }
    }
}
