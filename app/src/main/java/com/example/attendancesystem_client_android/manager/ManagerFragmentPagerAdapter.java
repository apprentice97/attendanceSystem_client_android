package com.example.attendancesystem_client_android.manager;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ManagerFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private ManagerFragment1 myFragment1 = null;
    private ManagerFragment2 myFragment2 = null;
    private ManagerFragment3 myFragment3 = null;
    private List<Fragment> fragments;


    public ManagerFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        myFragment1 = new ManagerFragment1();
        myFragment2 = new ManagerFragment2();
        myFragment3 = new ManagerFragment3();
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
            case ManagerMain.PAGE_ONE:
                fragment = myFragment1;
                break;
            case ManagerMain.PAGE_TWO:
                fragment = myFragment2;
                break;
            case ManagerMain.PAGE_THREE:
                fragment = myFragment3;
                break;
        }
        return fragment;
    }

}
