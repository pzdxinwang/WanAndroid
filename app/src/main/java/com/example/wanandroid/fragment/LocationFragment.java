package com.example.wanandroid.fragment;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TabpageLocationAdapter;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Location;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.JsonUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


public class LocationFragment extends BaseFragment implements HttpUtil.HttpCallbackListener {
    private List<WxArticleFragment> articleFragmentList;
    private List<Location> locationList;
    private List<String> tabStrings;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.fragment_location;
    }

    @Override
    public void initView() {
        toolbar = getView().findViewById(R.id.toolbar_head);
        viewPager = getView().findViewById(R.id.viewpage_location);
        tabLayout = getView().findViewById(R.id.tablyout_location);
        toolbar.setTitle("公众号");
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
    }

    @Override
    public void initData() {
        articleFragmentList = new ArrayList<>();
        tabStrings = new ArrayList<>();
        HttpUtil.get(Constant.CHAPTERS, 0, null, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            if (jsonObj.get("errorCode").getAsInt() == 0) {
                locationList = JsonUtil.getLocation(jsonObj);
            } else {
                ToastUtil.showShortToast(mContext, jsonObj.get("errorMsg").getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initFragments();
        }
    }

    @Override
    public void onFailure(Exception e) {

    }

    /**
     * 初始化页面
     */
    private void initFragments() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //添加数据
                for (Location location : locationList) {
                    articleFragmentList.add(WxArticleFragment.newInstance(String.valueOf(location.getId())));
                    tabStrings.add(location.getName());
                }
                //创建适配器
                TabpageLocationAdapter adapter = new TabpageLocationAdapter(getParentFragmentManager(), articleFragmentList, tabStrings);
                //设置ViewPager的适配器
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}