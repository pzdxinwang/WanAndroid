package com.example.wanandroid.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wanandroid.fragment.WxArticleFragment;

import java.util.List;

/**
 * 顶部分类栏
 */
public class TabpageLocationAdapter extends FragmentStatePagerAdapter {

    private List<WxArticleFragment> wxArticleFragmentList;
    private List<String> stringList;

    public TabpageLocationAdapter(FragmentManager fm, List<WxArticleFragment> wxArticleFragmentList, List<String> stringList) {
        super(fm);
        this.wxArticleFragmentList = wxArticleFragmentList;
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return wxArticleFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return wxArticleFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
