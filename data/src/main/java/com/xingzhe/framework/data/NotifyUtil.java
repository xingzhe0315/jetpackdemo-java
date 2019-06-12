package com.xingzhe.framework.data;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wumm on 2019/5/8.
 */
public class NotifyUtil {
    private static Context appContext;

    public static void initialize(Context context){
        appContext = context;
    }

    public static void notifyMessage(String message){
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }
}
