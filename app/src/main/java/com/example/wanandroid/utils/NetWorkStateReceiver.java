package com.example.wanandroid.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetWorkUtils.checkNetwork(context);
    }
}
