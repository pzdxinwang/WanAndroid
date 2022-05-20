package com.example.wanandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.activity.ArticleDetailActivity;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Article;
import com.example.wanandroid.model.Banner;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.example.wanandroid.view.BannerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示首页的文章列表
 */
public class HomeFragment extends BaseFragment implements OnRefreshListener,
        OnLoadMoreListener, HttpUtil.HttpCallbackListener, BannerView.OnItemClickListener {


    private List<Article> mArticleList;
    private ArticleAdapter mAdapter;
    private List<Banner> banners;

    private int curPage = 1;
    private String id;


    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar loadingProgressBar;

//    private static int BANNER_REQUEST_ID = 0;
    private static int ARTICLE_REQUEST_ID = 1;
//    private BannerView bannerView;
    private List<String> imgs;
    private List<String> titles;

    public static HomeFragment newInstance(String id) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    public void initView() {
        if (getArguments() != null) {
            id = getArguments().getString("id", "");
        }

        refreshLayout = getView().findViewById(R.id.refresh);
        recyclerView = getView().findViewById(R.id.recycler_view);
        loadingProgressBar = getView().findViewById(R.id.loading_view);

 //       bannerView = getView().findViewById(R.id.banner_view);


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
//        banners = new ArrayList<>();
//        imgs = new ArrayList<>();
//        titles = new ArrayList<>();
//        HttpUtil.get(Constant.BANNER, BANNER_REQUEST_ID, null, this);
        HttpUtil.get(Constant.ARTICLE + curPage + Constant.MYJSON, ARTICLE_REQUEST_ID, null, this);
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
        //通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容
        mAdapter.notifyDataSetChanged();
        ++curPage;
        HttpUtil.get(Constant.ARTICLE + curPage + Constant.MYJSON, ARTICLE_REQUEST_ID, null, this);
    }

    /**
     * 加载更多文章
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        ++curPage;
        HttpUtil.get(Constant.ARTICLE + curPage + Constant.MYJSON, ARTICLE_REQUEST_ID, null, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        ((Activity) mContext).runOnUiThread(() -> {
            if (JsonUtil.getErrorCode(response) == 0) {
                if (requestId == ARTICLE_REQUEST_ID) {
                    //添加数据
                    mArticleList.addAll(JsonUtil.getArticles(response));
                    mAdapter.notifyDataSetChanged();
                    loadingProgressBar.cancelLongPress();
                    loadingProgressBar.setVisibility(View.GONE);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                }
//                if (requestId == BANNER_REQUEST_ID) {
//                    banners.addAll(JsonUtil.getBanners(response));
//                    mAdapter.notifyDataSetChanged();
//                    for (int i = 0; i < banners.size(); i++) {
//                        imgs.add(banners.get(i).getImagePath());
//                        titles.add(banners.get(i).getTitle());
//                    }
//                    bannerView.setImageUrl(imgs);
//                    bannerView.setTitle(titles);
//
//                    bannerView.setmIndicaterStyle(R.drawable.logo);
//                    bannerView.build();
//                }
            }
        });
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onItemClick(int index) {
        Intent intent = new Intent(mContext, ArticleDetailActivity.class);
        intent.putExtra("title", titles.get(index));
        intent.putExtra("link", banners.get(index).getUrl());
        mContext.startActivity(intent);
    }
}