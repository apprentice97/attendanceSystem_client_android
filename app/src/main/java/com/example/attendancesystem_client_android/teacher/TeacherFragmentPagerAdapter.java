package com.example.attendancesystem_client_android.teacher;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TeacherFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private TeacherFragment1 myFragment1 = null;
    private TeacherFragment2 myFragment2 = null;
    private TeacherFragment3 myFragment3 = null;
    private List<Fragment> fragments;




    public TeacherFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        myFragment1 = new TeacherFragment1();
        myFragment2 = new TeacherFragment2();
        myFragment3 = new TeacherFragment3();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case TeacherMain.PAGE_ONE:
                fragment = myFragment1;
                break;
            case TeacherMain.PAGE_TWO:
                fragment = myFragment2;
                break;
            case TeacherMain.PAGE_THREE:
                fragment = myFragment3;
                break;
        }
        return fragment;
    }

}
