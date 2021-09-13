package com.example.wanandroid.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.application.MyApplication;
import com.example.wanandroid.utils.NetWorkStateReceiver;

public abstract class BaseFragment extends Fragment {
    private IntentFilter intentFilter;
    private NetWorkStateReceiver netWorkStateReceiver;
    public Context mContext;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(getLayout(), parent, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext() != null ? getContext() : MyApplication.getInstance();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn,CONNECTIVITY_CHANGE");
        netWorkStateReceiver = new NetWorkStateReceiver();
        //注册
        mContext.registerReceiver(netWorkStateReceiver,intentFilter);
        initView();
        initData();
    }

    /**
     * 获取布局文件
     */
    public abstract int getLayout();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();
}
