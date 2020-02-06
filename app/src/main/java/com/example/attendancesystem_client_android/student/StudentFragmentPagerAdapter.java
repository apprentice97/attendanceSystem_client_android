package com.example.attendancesystem_client_android.student;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class StudentFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private StudentFragment1 myFragment1 = null;
    private StudentFragment2 myFragment2 = null;
    private StudentFragment3 myFragment3 = null;
    private List<Fragment> fragments;


    public StudentFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        myFragment1 = new StudentFragment1();
        myFragment2 = new StudentFragment2();
        myFragment3 = new StudentFragment3();
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
            case StudentMain.PAGE_ONE:
                fragment = myFragment1;
                break;
            case StudentMain.PAGE_TWO:
                fragment = myFragment2;
                break;
            case StudentMain.PAGE_THREE:
                fragment = myFragment3;
                break;
        }
        return fragment;
    }

}
