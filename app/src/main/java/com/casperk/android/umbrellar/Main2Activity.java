package com.casperk.android.umbrellar;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.casperk.android.umbrellar.fragments.TabForecastOverViewFragment;
import com.casperk.android.umbrellar.fragments.TabScheduleFragment;
import com.casperk.android.umbrellar.models.WeatherForecast;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG, "onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabForecastOverViewFragment(), "Overzicht");
        adapter.addFragment(new TabScheduleFragment(), "Planner");
        viewPager.setAdapter(adapter);
    }
}
