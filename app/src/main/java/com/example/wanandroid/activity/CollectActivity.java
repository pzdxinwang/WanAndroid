package com.example.wanandroid.activity;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.CollectAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Article;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.example.wanandroid.utils.SharePreferencesUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏页面
 */
public class CollectActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, HttpUtil.HttpCallbackListener {
    private List<Article> articleList;
    private CollectAdapter mAdapter;
    private int curPage;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar loadingProgressBar;

    @Override
    public int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recycler_view);
        loadingProgressBar = findViewById(R.id.loading_view);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableRefresh(true);//启用上拉刷新功能
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableLoadMore(true);//启用下拉加载功能

        //设置适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleList = new ArrayList<>();
        mAdapter = new CollectAdapter(articleList, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

        curPage = 0;
        String path = Constant.COLLECT_LIST + curPage + Constant.MYJSON;
        String localCookie = SharePreferencesUtil.getString(this, "Cookie", null);
        HttpUtil.get(path, 0, localCookie, this);
        loadingProgressBar.show();
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 实现下拉刷新
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        articleList.clear();
        curPage = 0;
        String path = Constant.COLLECT_LIST + curPage + Constant.MYJSON;
        String localCookie = SharePreferencesUtil.getString(this, "Cookie", null);
        HttpUtil.get(path, 0, localCookie, this);
    }

    /**
     * 实现下拉加载
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        curPage++;
        String path = Constant.COLLECT_LIST + curPage + Constant.MYJSON;
        String localCookie = SharePreferencesUtil.getString(this, "Cookie", null);
        HttpUtil.get(path, 0, localCookie, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        if (requestId == 0) {
            runOnUiThread(() -> {
                if (JsonUtil.getErrorCode(response) == 0) {
                    //添加全部的数据
                    articleList.addAll(JsonUtil.getArticles(response));
                    mAdapter.notifyDataSetChanged();
                    loadingProgressBar.cancelLongPress();
                    loadingProgressBar.setVisibility(View.GONE);
                    refreshLayout.finishRefresh();//结束刷新
                    refreshLayout.finishLoadMore();//结束加载
                } else {
                    ToastUtil.showShortToast(CollectActivity.this, JsonUtil.getErrorMsg(response));
                }
            });
        }
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

}
