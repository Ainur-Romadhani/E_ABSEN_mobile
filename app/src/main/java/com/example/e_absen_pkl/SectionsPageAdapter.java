package com.example.e_absen_pkl;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private  List<Fragment> mFragmentList;
    private  List<String> mFragmentTitleList;


    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentList = new ArrayList<>();
        this.mFragmentTitleList = new ArrayList<>();
    }


    public void addFragment(Fragment fragment, String title ,String data) {
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
