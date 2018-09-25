package com.dingyl.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dingyl.wanandroid.fragment.ProjectEntryFragment;

import java.util.ArrayList;

public class ProjectViewPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<ProjectEntryFragment> fragments;
    private ArrayList<String> titles;
    public ProjectViewPagerAdapter(FragmentManager fm,ArrayList<String> titles,ArrayList<ProjectEntryFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }
}