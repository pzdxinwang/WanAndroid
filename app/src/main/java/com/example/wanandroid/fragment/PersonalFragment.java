package com.example.wanandroid.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.wanandroid.R;
import com.example.wanandroid.activity.AboutAuthorActivity;
import com.example.wanandroid.activity.CollectActivity;
import com.example.wanandroid.activity.LoginActivity;
import com.example.wanandroid.activity.ToDoActivity;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.SharePreferencesUtil;
import com.example.wanandroid.utils.ToastUtil;

public class PersonalFragment extends BaseFragment implements View.OnClickListener,
        HttpUtil.HttpCallbackListener, DialogInterface.OnClickListener {
    private TextView goLogin, phrase, collect, logoff, wanAndroid, aboutAuthor,toDo;
    private ImageView headPortrait;

    @Override
    public int getLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initData() {

    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        goLogin = getActivity().findViewById(R.id.tv_go_login);
        headPortrait = getActivity().findViewById(R.id.iv_head_portrait);
        collect = getActivity().findViewById(R.id.tv_collect);
        logoff = getActivity().findViewById(R.id.tv_logoff);
        wanAndroid = getActivity().findViewById(R.id.tv_wanandroid);
        aboutAuthor = getActivity().findViewById(R.id.tv_about_author);
        phrase = getActivity().findViewById(R.id.tv_phrase);
        toDo = getActivity().findViewById(R.id.tv_personal_todo);

        goLogin.setOnClickListener(this);
        collect.setOnClickListener(this);
        logoff.setOnClickListener(this);
        wanAndroid.setOnClickListener(this);
        aboutAuthor.setOnClickListener(this);
        headPortrait.setOnClickListener(this);
        toDo.setOnClickListener(this);
        phrase.setOnClickListener(this);
        toDo.setOnClickListener(this);


        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(this).load(R.drawable.logo).apply(mRequestOptions).into(headPortrait);
        if (SharePreferencesUtil.getBoolean(mContext, "isLogin", false)) {
            goLogin.setText(SharePreferencesUtil.getString(mContext, "username", "登录/注册"));
        } else {
            goLogin.setText("登录/注册");
        }
    }

    /**
     * 点击事件
     *
     * @param view 控件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录/注册、用户名
            case R.id.tv_go_login:
                //已经登录
                if (SharePreferencesUtil.getBoolean(mContext, "isLogin", false)) {
                    ToastUtil.showShortToast(mContext, "暂时还不支持修改用户名");
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                break;
            //头像
            case R.id.iv_head_portrait:
                ToastUtil.showShortToast(mContext, "暂时还不支持更换头像");
                break;
            //励志短语
            case R.id.tv_phrase:
                ToastUtil.showShortToast(mContext, "暂时还不支持更换励志短语");
                break;
            //关于作者
            case R.id.tv_about_author:
                startActivity(new Intent(mContext, AboutAuthorActivity.class));
                break;
            //收藏列表
            case R.id.tv_collect:
                if (SharePreferencesUtil.getBoolean(mContext, "isLogin", false)){
                    startActivity(new Intent(mContext, CollectActivity.class));
                }else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
//                ToastUtil.showShortToast(mContext,"还没写好");
                break;
            //wanandroid网站
            case R.id.tv_wanandroid:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constant.URL));
                startActivity(intent);
                break;
                //待办事项
            case R.id.tv_personal_todo:
                startActivity(new Intent(mContext, ToDoActivity.class));
                break;
            //退出登录
            case R.id.tv_logoff:
                if (SharePreferencesUtil.getBoolean(mContext, "isLogin", false)) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("确认退出？")
                            .setPositiveButton("确认", this)
                            .setCancelable(true)
                            .create()
                            .show();
                } else {
                    ToastUtil.showShortToast(mContext, "你还没有登陆");
                }
                break;
//                ToastUtil.showShortToast(mContext,"正在加班制作中");
        }
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        switch (requestId) {
            case 0:
                ((Activity) mContext).runOnUiThread(() -> {
                    SharePreferencesUtil.putBoolean(mContext, "isLogin", false);
                    SharePreferencesUtil.putString(mContext, "Cookie", "");
                    initView();
                });
                break;
            default:
                break;
        }

    }

    @Override
    public void onFailure(Exception e) {

    }

    /**
     * 确认退出
     *
     * @param dialogInterface
     * @param i
     */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        HttpUtil.get(Constant.LOGOFF, 0, null, this);
    }

}