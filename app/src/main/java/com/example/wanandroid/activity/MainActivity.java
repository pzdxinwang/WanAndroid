package com.example.wanandroid.activity;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wanandroid.R;
import com.example.wanandroid.fragment.HomeFragment;
import com.example.wanandroid.fragment.KnowledgeFragment;
import com.example.wanandroid.fragment.LocationFragment;
import com.example.wanandroid.fragment.PersonalFragment;
import com.example.wanandroid.fragment.ProjectFragment;
import com.example.wanandroid.utils.HttpUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements HttpUtil.HttpCallbackListener {


    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private long Time = System.currentTimeMillis();

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        viewPager2 = findViewById(R.id.viewpage2);
        bottomNavigationView = findViewById(R.id.bottombar_main);
/**
 * 设置list集合，数据是bottom所需要的几个按钮
 */
        ArrayList<Fragment> bottomList = new ArrayList<>();
        bottomList.add(new HomeFragment());
        bottomList.add(new LocationFragment());
        bottomList.add(new ProjectFragment());
        bottomList.add(new KnowledgeFragment());
        bottomList.add(new PersonalFragment());
/**
 * 设置适配器
 */
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return bottomList.get(position);
            }

            @Override
            public int getItemCount() {
                return bottomList.size();
            }
        });

        /**
         *监听ViewPager2里的页面切换事件.
         */
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                viewPager2.setCurrentItem(item.getOrder());
                return true;
            }
        });
    }

    /**
     * 退出程序
     */
    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - Time > 1000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Time = nowTime;
        } else {
            finish();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {

    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

}