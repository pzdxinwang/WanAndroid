package com.example.wanandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 受学长指导，toast工具类
 */
public class ToastUtil {
    /**
     *
     * @param context 上下文对象
     * @param s 要显示的内容
     */
    public static void showShortToast(Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}