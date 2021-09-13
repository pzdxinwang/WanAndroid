package com.example.wanandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ProjectAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Project;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ProjectPresentFragment extends BaseFragment implements HttpUtil.HttpCallbackListener, OnRefreshListener, OnLoadMoreListener {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private ContentLoadingProgressBar loadingProgressBar;
    private String id;
    private ProjectAdapter projectAdapter;
    private List<Project> projectList;
    private int curPage = 1;
    private static int PROJECT_PRESENT_REQUEST_ID = 0;

    public static ProjectPresentFragment newInstance(String id) {
        ProjectPresentFragment fragment = new ProjectPresentFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_project_present;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            id = getArguments().getString("id", "");
        }
        recyclerView = getView().findViewById(R.id.recycler_view);
        loadingProgressBar = getView().findViewById(R.id.loading_view);
        refreshLayout = getView().findViewById(R.id.refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        projectList = new ArrayList<>();
        projectAdapter = new ProjectAdapter(mContext, projectList);
        recyclerView.setAdapter(projectAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {

        //项目列表数据 https://www.wanandroid.com/project/list/1/json?cid=294
        HttpUtil.get(Constant.PROJECT_List + "/" + curPage + "/json?cid=" + id, PROJECT_PRESENT_REQUEST_ID, null, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                if (requestId == PROJECT_PRESENT_REQUEST_ID) {
                    projectList.addAll(JsonUtil.getProjects(jsonObject));
                    projectAdapter.notifyDataSetChanged();
                    loadingProgressBar.cancelLongPress();
                    loadingProgressBar.setVisibility(View.GONE);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        ++curPage;
        HttpUtil.get(Constant.PROJECT_List + "/" + curPage + "/json?cid=" + id, PROJECT_PRESENT_REQUEST_ID, null, this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        projectList.clear();
        projectAdapter.notifyDataSetChanged();
        HttpUtil.get(Constant.PROJECT_List +"/"+curPage+"/json?cid="+id, PROJECT_PRESENT_REQUEST_ID, null, this);
    }
}
