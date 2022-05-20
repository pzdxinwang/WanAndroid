package com.example.wanandroid.fragment;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TabPageProjectAdapter;
import com.example.wanandroid.adapter.TabpageLocationAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.ProjectPresent;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends BaseFragment implements HttpUtil.HttpCallbackListener, OnLoadMoreListener, OnRefreshListener {
    private List<ProjectPresent> projectPresents;
    private List<ProjectPresentFragment> projectPresentFragmentList;
    private Toolbar toolbar;
    private List<String> tabName;
    private ViewPager viewPager;
    private final int PROJECT_REQUEST_ID = 0;
    private final int TABPAGE_REQUEST_ID = 1;
    private TabLayout tabLayout;
    private int curPage;

    @Override
    public int getLayout() {
        return R.layout.fragment_project;
    }

    @Override
    public void initView() {
        toolbar = getView().findViewById(R.id.toolbar_head);
        tabLayout = getView().findViewById(R.id.tablayout_project);
        viewPager = getView().findViewById(R.id.viewPager_project);
    }

    @Override
    public void initData() {
        toolbar.setTitle("项目");

        tabName = new ArrayList<>();
        projectPresentFragmentList = new ArrayList<>();
        curPage = 1;
        //项目表单的网络请求
        HttpUtil.get(Constant.PROJECT, TABPAGE_REQUEST_ID, null, this);


    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        try {
            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            if (requestId == TABPAGE_REQUEST_ID) {
                projectPresents = JsonUtil.getProjectPresents(jsonObject);
                for (ProjectPresent p : projectPresents) {
                    tabName.add(p.getName());
                }
            } else {
                ToastUtil.showShortToast(mContext, jsonObject.get("errorMsg").getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //初始化碎片
            initFragment();
        }

    }

    /**
     * 这个方法初始化了碎片 项目上面的tabLayout
     */
    private void initFragment() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (ProjectPresent pp :
                        projectPresents) {
                    projectPresentFragmentList.add(ProjectPresentFragment.newInstance(String.valueOf(pp.getId())));
                    tabName.add(pp.getName());

                    //设置适配器
                    TabPageProjectAdapter adapter = new TabPageProjectAdapter(getParentFragmentManager(), projectPresentFragmentList, tabName);
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                }
            }
        });
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        ++curPage;
//        HttpUtil.get("project/list/" + curPage + "/json?cid=" + cid, 0, null, this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        //关于加载更多和刷新，这里有点偷工减料了，选择接口应该是PROJECT_List，但是最上面我直接选择了PROJECT
        //导致这部分有点问题，要是以后要完成这部分内容可以看看homefragment怎么写的
//
//        curPage = 1;
//        projectPresentFragmentList.clear();
//        projectPresents.notifyAll();
//        HttpUtil.get( + curPage + "/json?cid=" + cid, 0, null, this);
    }
}
