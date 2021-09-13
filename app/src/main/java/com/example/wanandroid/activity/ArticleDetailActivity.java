package com.example.wanandroid.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.example.wanandroid.R;

public class ArticleDetailActivity extends BaseActivity {
    private ProgressBar mProgressBar;
    private WebView webView;
    private Drawable back;
    private Toolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.activity_article_detail;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        webView = findViewById(R.id.wv_article_webview);
        toolbar = findViewById(R.id.toolbar_head);
        back = getDrawable(R.drawable.ic_back);

        toolbar.setTitle(getIntent().getStringExtra("title"));//设置头标题
        setSupportActionBar(toolbar);
        //可以通过图标返回
        toolbar.setNavigationIcon(back);
        toolbar.setNavigationOnClickListener(view -> finish());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);

        //配置webview缩放参数
        webView.getSettings().setUseWideViewPort(true);//设置true,才能让Webivew支持<meta>标签的viewport属性
        webView.getSettings().setSupportZoom(true);//设置可以支持缩放
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    public void initData() {
        webView.loadUrl(getIntent().getStringExtra("link"));
        Log.e("coder", getIntent().getStringExtra("link"));
    }

    /**
     * 可以返回网站的上一页而不是直接退回app
     */
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}