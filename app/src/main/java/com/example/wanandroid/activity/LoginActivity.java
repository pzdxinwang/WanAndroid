package com.example.wanandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wanandroid.R;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.SharePreferencesUtil;
import com.example.wanandroid.utils.StringUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 登录界面的实现
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpUtil.HttpCallbackListener {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private AppCompatTextView tvRegister;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        AppCompatTextView tvGoRegister = findViewById(R.id.tv_register);
        btnLogin.setOnClickListener(this);
        tvGoRegister.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //去注册
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
                //去登录
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        String useName = etUsername.getText().toString();
        String usePassword = etPassword.getText().toString();
        //判空
        if (StringUtil.isEmpty(useName)) {
            ToastUtil.showShortToast(this, "请输入用户名!");
        }
        if (StringUtil.isEmpty(usePassword)) {
            ToastUtil.showShortToast(this, "请输入密码！");
        }

        JsonObject params = new JsonObject();
        params.addProperty("username", useName);
        params.addProperty("password", usePassword);
        HttpUtil.post(Constant.LOGIN, 0, params, null, true, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
        //函数式编程，更新UI操作
        runOnUiThread(() -> {
            if (jsonObj.get("errorCode").getAsInt() == 0) {
                ToastUtil.showShortToast(LoginActivity.this, "登陆成功");
                //登录成功之后将用户的用户名、密码、cookie保存下来
                SharePreferencesUtil.putString(LoginActivity.this, "username", etUsername.getText().toString());
                SharePreferencesUtil.putString(LoginActivity.this, "password", etPassword.getText().toString());
                SharePreferencesUtil.putBoolean(LoginActivity.this, "isLogin", true);
                SharePreferencesUtil.putString(LoginActivity.this, "Cookie", cookie);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                ToastUtil.showShortToast(LoginActivity.this, jsonObj.get("errorMsg").getAsString());
            }
        });
    }

    @Override
    public void onFailure(Exception e) {
        ToastUtil.showShortToast(this, e.toString());
    }
}