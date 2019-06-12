package com.xingzhe.ui_libaray;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

/**
 * Created by wumm on 2019/5/14.
 */
public class ViewModelCreator {
    public static ViewModel createViewModel(Context context,Class clazz){
        return ViewModelProvider.AndroidViewModelFactory.getInstance((Application) context.getApplicationContext()).create(clazz);
    }
}
