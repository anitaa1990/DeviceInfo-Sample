package com.deviceinfosample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.deviceinfosample.R;
import com.deviceinfosample.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addTitle("Location");
        adapter.addTitle("App");
        adapter.addTitle("Ads");
        adapter.addTitle("Battery");
        adapter.addTitle("Device");
        adapter.addTitle("Memory");
        adapter.addTitle("Network");
        adapter.addTitle("Installed Apps");
        adapter.addTitle("Contacts");
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }
}
