package com.example.wanandroid.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TabpageLocationAdapter;
import com.example.wanandroid.fragment.WxArticleFragment;
import com.example.wanandroid.model.Tree;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeActivity extends BaseActivity {
    private List<WxArticleFragment> fragments;
    private List<String> tabStrings;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.activity_knowledge;
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_head);
        tabLayout = findViewById(R.id.tabLayout_knowlegeAc);
        viewPager = findViewById(R.id.viewpage_knowledgeAc);

        toolbar.setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        tabStrings = new ArrayList<>();
        List<Tree> trees = getIntent().getParcelableArrayListExtra("items");
        //通过循环遍历体系标题并设置对应的名字
        for (int i =0;i<trees.size(); i++){
            fragments.add(WxArticleFragment.newInstance(trees.get(i).getId()));
            tabStrings.add(trees.get(i).getName());
        }
        TabpageLocationAdapter adapter = new TabpageLocationAdapter(getSupportFragmentManager(), fragments, tabStrings);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}