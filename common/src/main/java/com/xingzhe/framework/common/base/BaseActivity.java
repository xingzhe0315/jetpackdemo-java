package com.xingzhe.framework.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wumm on 2019/6/13.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyActivityCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifyActivityStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyActivityResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        notifyActivityPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifyActivityStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notifyActivityDestroy();
    }

    public void startActivityForResult(Intent intent, int requestCode, IActivityResultListener listener) {
        addActivityResultListener(requestCode, listener);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityResultListeners != null) {
            IActivityResultListener resultListener = mActivityResultListeners.get(requestCode);
            if (resultListener != null) {
                resultListener.onActivityResult(this, requestCode, resultCode, data);
                removeActivityResultListener(requestCode);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void notifyActivityCreate(@Nullable Bundle savedInstanceState) {
        if (mLifeCycleListeners != null) {
            for (IActivityLifeCycleListener mLifeCycleListener : mLifeCycleListeners) {
                mLifeCycleListener.onActivityCreated(savedInstanceState);
            }
        }
    }

    private void notifyActivityStart() {
        if (mLifeCycleListeners != null) {
            for (IActivityLifeCycleListener mLifeCycleListener : mLifeCycleListeners) {
                mLifeCycleListener.onActivityStart();
            }
        }
    }

    private void notifyActivityResume() {
        if (mLifeCycleListeners != null) {
            for (IActivityLifeCycleListener mLifeCycleListener : mLifeCycleListeners) {
                mLifeCycleListener.onActivityResume();
            }
        }
    }

    private void notifyActivityPause() {
        if (mLifeCycleListeners != null) {
            for (IActivityLifeCycleListener mLifeCycleListener : mLifeCycleListeners) {
                mLifeCycleListener.onActivityPause();
            }
        }
    }

    private void notifyActivityStop() {
        if (mLifeCycleListeners != null) {
            for (IActivityLifeCycleListener mLifeCycleListener : mLifeCycleListeners) {
                mLifeCycleListener.onActivityStop();
            }
        }
    }

    private void notifyActivityDestroy() {
        if (mLifeCycleListeners != null) {
            for (IActivityLifeCycleListener mLifeCycleListener : mLifeCycleListeners) {
                mLifeCycleListener.onActivityDestroy();
            }
        }
        clearLifeCycleListener();
        clearActivityResultListener();
    }

    private List<IActivityLifeCycleListener> mLifeCycleListeners;
    private SparseArray<IActivityResultListener> mActivityResultListeners;

    public void addLifeCycleListener(IActivityLifeCycleListener lifeCycleListener) {
        if (mLifeCycleListeners == null) {
            mLifeCycleListeners = new ArrayList<>();
        }
        mLifeCycleListeners.add(lifeCycleListener);
    }

    public void removeLifeCycleListener(IActivityLifeCycleListener lifeCycleListener) {
        if (mLifeCycleListeners == null) {
            mLifeCycleListeners.remove(lifeCycleListener);
        }
    }

    public void clearLifeCycleListener() {
        if (mLifeCycleListeners != null) {
            mLifeCycleListeners.clear();
            mLifeCycleListeners = null;
        }
    }

    public void addActivityResultListener(int requestCode, IActivityResultListener activityResultListener) {
        if (mActivityResultListeners == null) {
            mActivityResultListeners = new SparseArray<>();
        }
        mActivityResultListeners.put(requestCode, activityResultListener);
    }

    public void removeActivityResultListener(int requestCode) {
        if (mActivityResultListeners != null) {
            mActivityResultListeners.remove(requestCode);
        }
    }

    public void clearActivityResultListener() {
        if (mActivityResultListeners != null) {
            mActivityResultListeners.clear();
            mActivityResultListeners = null;
        }
    }

    interface IActivityLifeCycleListener {
        void onActivityCreated(@Nullable Bundle savedInstanceState);

        void onActivityStart();

        void onActivityResume();

        void onActivityPause();

        void onActivityStop();

        void onActivityDestroy();
    }

    interface IActivityResultListener {
        void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
    }

    class BaseActivityCycleListener implements IActivityLifeCycleListener {

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStart() {

        }

        @Override
        public void onActivityResume() {

        }

        @Override
        public void onActivityPause() {

        }

        @Override
        public void onActivityStop() {

        }

        @Override
        public void onActivityDestroy() {

        }
    }
}
