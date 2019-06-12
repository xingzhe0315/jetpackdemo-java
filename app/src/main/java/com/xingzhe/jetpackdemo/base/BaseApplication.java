package com.xingzhe.jetpackdemo.base;

import android.app.Application;

import com.xingzhe.framework.data.NotifyUtil;

/**
 * Created by wumm on 2019/5/8.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NotifyUtil.initialize(this);
    }
}
