package com.xingzhe.jetpackdemo;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;


public class ContextUtil {

    /**
     * 获取Context对应的Activity
     *
     * @param context
     * @return
     */
    public static Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
