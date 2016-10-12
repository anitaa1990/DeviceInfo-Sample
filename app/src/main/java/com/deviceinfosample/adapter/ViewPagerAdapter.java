package com.deviceinfosample.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.deviceinfosample.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<String> titles = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addTitle(String title) {
        titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position);
    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
