package com.example.e_absen_pkl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class History extends AppCompatActivity {

    private static final String TAG = "History";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    TextView back;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
//
        back = findViewById(R.id.back);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);



    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Belum Di verifikasi","0");
        adapter.addFragment(new Tab1Fragment(), "Di Setujui","1");
        adapter.addFragment(new Tab1Fragment(), "Di Tolak","2");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
