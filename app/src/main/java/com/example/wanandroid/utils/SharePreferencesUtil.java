package com.example.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

/**
 * 使用SharePreferences,进行本地数据持久化
 * PreferenceManager.GetDefaultSharedPreferences已经在API29里弃用了，
 * 得使用androidx.preference.PreferenceManager
 */
public class SharePreferencesUtil {
    //添加字符串
    public static void putString(Context context, String key, String val) {
        //调用SharedPreferences的edit()方法来获取一个SharedPreferences.Editor对象
        SharedPreferences.Editor editor = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, val);//添加数据
        editor.apply();//将添加的数据提交，完成数据存储操作
    }

    public static String getString(Context context, String key, String defVal) {
        SharedPreferences sp = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defVal);
    }

    //添加布尔型数据
    public static void putBoolean(Context context, String key, boolean val) {
        SharedPreferences.Editor editor = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static Boolean getBoolean(Context context, String key, boolean defVal) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defVal);
    }
}
