package com.example.wanandroid.application;

import android.app.Application;

/**
 * 扩展了Application类的框架代码，并把它实现为一个单态
 * 作用：
 *   · 对Android运行时广播的应用程序级事件做出相应。
 * 　· 在应用程序组件之间传递对象。
 * 　· 管理和维护多个应用程序组件使用的资源。
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        if (mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
