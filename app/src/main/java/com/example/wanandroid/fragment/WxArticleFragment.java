package com.example.wanandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Article;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class WxArticleFragment extends BaseFragment implements OnRefreshListener,
        OnLoadMoreListener, HttpUtil.HttpCallbackListener {

    private List<Article> mArticleList;
    private ArticleAdapter mAdapter;

    private int curPage = 1;
    private String id;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar loadingProgressBar;

    public static WxArticleFragment newInstance(String id) {
        WxArticleFragment fragment = new WxArticleFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_article_list;
    }

    public void initView() {
        if (getArguments() != null) {
            id = getArguments().getString("id", "");
        }

        refreshLayout = getView().findViewById(R.id.refresh);
        recyclerView = getView().findViewById(R.id.recycler_view);
        loadingProgressBar = getView().findViewById(R.id.loading_view);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        //准备数据
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mArticleList = new ArrayList<>();
        mAdapter = new ArticleAdapter(mContext, mArticleList);
        //将adapter设置进recyclerview
        recyclerView.setAdapter(mAdapter);
    }

    public void initData() {
        HttpUtil.get(Constant.CHAPTERS_ARTICLE + id + "/" + curPage + Constant.MYJSON, 0, null, this);
        loadingProgressBar.show();
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mArticleList.clear();
        mAdapter.notifyDataSetChanged();
        ++curPage;
        HttpUtil.get(Constant.CHAPTERS_ARTICLE + id + "/" + curPage + Constant.MYJSON, 0, null, this);
    }

    /**
     * 加载更多文章
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        ++curPage;
        HttpUtil.get(Constant.CHAPTERS_ARTICLE + id + "/" + curPage + Constant.MYJSON, 0, null, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        ((Activity) mContext).runOnUiThread(() -> {
            if (JsonUtil.getErrorCode(response) == 0) {
                mArticleList.addAll(JsonUtil.getArticles(response));
                mAdapter.notifyDataSetChanged();
                loadingProgressBar.cancelLongPress();
                loadingProgressBar.setVisibility(View.GONE);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            } else {
                ToastUtil.showShortToast(mContext, JsonUtil.getErrorMsg(response));
            }
        });
    }

    @Override
    public void onFailure(Exception e) {
e.printStackTrace();
    }
}