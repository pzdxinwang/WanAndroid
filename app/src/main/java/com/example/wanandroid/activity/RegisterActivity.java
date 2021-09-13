package com.example.wanandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
 * 注册界面的实现
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, HttpUtil.HttpCallbackListener {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPasswordAgain = findViewById(R.id.et_password_again);
        Button btnRegister = findViewById(R.id.btn_register);
        AppCompatTextView tvGoLogin = findViewById(R.id.tv_goLogin);
        btnRegister.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goLogin:
                //转到登录界面
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_register:
                //注册
                register();
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        //获取用户输入的信息
        String userName = etUsername.getText().toString();
        String Password = etPassword.getText().toString();
        String rePassword = etPasswordAgain.getText().toString();

        //判断是否为空
        if (StringUtil.isEmpty(userName)) {
            ToastUtil.showShortToast(this,"用户名不能为空！");
            return;
        }
        if (StringUtil.isEmpty(Password)) {
            ToastUtil.showShortToast(this,"密码不能为空！");
            return;
        }
        if (StringUtil.isEmpty(rePassword)) {
            ToastUtil.showShortToast(this,"请再次确认密码！");
            return;
        }
        if (!Password.equals(rePassword)) {
            ToastUtil.showShortToast(this,"两次密码输入不一致，请重新输入");
            return;
        }
        /*
        这个addProperty()里的property一定要对哇，这个是post上去的key，要对应好API里要接收的key 折磨了我好久
         */
        JsonObject params = new JsonObject();
        params.addProperty("username", userName);
        params.addProperty("password", Password);
        params.addProperty("repassword", rePassword);
        HttpUtil.post(Constant.REGISTER, 0, params, null, false, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            runOnUiThread(() -> {
                if (jsonObj.get("errorCode").getAsInt() == 0) {
                    ToastUtil.showShortToast(this,"注册成功");
                    SharePreferencesUtil.putString(RegisterActivity.this, "username", etUsername.getText().toString());
                    SharePreferencesUtil.putString(RegisterActivity.this, "password", etPassword.getText().toString());
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ToastUtil.showShortToast(RegisterActivity.this, jsonObj.get("errorMsg").getAsString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //当请求不成功时
    @Override
    public void onFailure(Exception e) {
        Log.e("coder", e.getMessage());
    }
}