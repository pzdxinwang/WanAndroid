package com.example.wanandroid.fragment;

import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TreeAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Tree;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, HttpUtil.HttpCallbackListener {
    private List<Tree> trees;
    private TreeAdapter treeAdapter;

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    public int getLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public void initView() {
        toolbar = getView().findViewById(R.id.toolbar_head);
        refreshLayout = getView().findViewById(R.id.refresh_knowledge);
        recyclerView = getView().findViewById(R.id.recyclerview_knowledge);

        toolbar.setTitle("体系");

        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //准备数据
        trees = new ArrayList<>();
        treeAdapter = new TreeAdapter(mContext, trees);
        //将adapter设置进recycleview
        recyclerView.setAdapter(treeAdapter);

        HttpUtil.get(Constant.TREE, 0, null, this);
    }

    @Override
    public void onRefresh() {
        trees.clear();
        treeAdapter.notifyDataSetChanged();
        HttpUtil.get(Constant.TREE, 0, null, this);

    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            if (jsonObj.get("errorCode").getAsInt() == 0) {
                trees.addAll(JsonUtil.getTrees(jsonObj));
            } else {
                Toast.makeText(mContext, jsonObj.get("errorMsg").getAsString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((Activity) mContext).runOnUiThread(() -> {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                treeAdapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}