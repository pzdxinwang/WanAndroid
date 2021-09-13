package com.example.wanandroid.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wanandroid.fragment.ProjectPresentFragment;

import java.util.List;

public class TabPageProjectAdapter extends FragmentStatePagerAdapter {

    private List<ProjectPresentFragment> projectFragmentList;
    private List<String> stringList;

    public TabPageProjectAdapter(@NonNull FragmentManager fm,List<ProjectPresentFragment> projectFragmentList,List<String> stringList) {
        super(fm);
        this.projectFragmentList = projectFragmentList;
        this.stringList = stringList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return projectFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return projectFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
