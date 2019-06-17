package com.xingzhe.ui_libaray.dataview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by wumm on 2019/3/20.
 */
public abstract class LoadingView extends RelativeLayout {

    public static final int STATE_LOADING = 0;
    public static final int STATE_ERROR = -1;
    public static final int STATE_EMPTY = 1;

    private int state;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    private DataRetryHandler retryHandler;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        showLoading();
    }

    public void setState(int state) {
        this.state = state;
        switch (state) {
            case STATE_LOADING:
                showLoading();
                break;
            case STATE_ERROR:
                showError();
                break;
            case STATE_EMPTY:
                showEmpty();
        }
    }

    private void showLoading() {
        if (mLoadingView == null) {
            mLoadingView = createLoadingView();
            mLoadingView.setLayoutParams(createLayoutParams());
        }
        displayView(mLoadingView);
    }

    protected abstract View createLoadingView();

    private void showError() {
        if (mErrorView == null) {
            mErrorView = createErrorView();
            mErrorView.setLayoutParams(createLayoutParams());
            mErrorView.setOnClickListener(v -> {
                if (retryHandler != null) {
                    retryHandler.onHandleRetry();
                    showLoading();
                }
            });
        }

        displayView(mErrorView);
    }

    protected abstract View createErrorView();

    private void showEmpty() {
        if (mEmptyView == null) {
            mEmptyView = createEmptyView();
            mEmptyView.setLayoutParams(createLayoutParams());
        }
        displayView(mEmptyView);
    }

    protected abstract View createEmptyView();

    private void displayView(View view) {
        removeAllViews();
        if (view != null) {
            addView(view);
        }
    }

    private LayoutParams createLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setRetryHandler(DataRetryHandler retryHandler) {
        this.retryHandler = retryHandler;
    }
}
