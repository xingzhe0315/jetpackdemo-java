package com.xingzhe.ui_libaray.dataview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.xingzhe.ui_libaray.R;

/**
 * Created by wumm on 2019/6/13.
 */
public class DefaultLoadingView extends LoadingView {
    public DefaultLoadingView(Context context) {
        this(context, null);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createLoadingView() {
        return View.inflate(getContext(), R.layout.view_state_loading, null);
    }

    @Override
    protected View createErrorView() {
        return View.inflate(getContext(), R.layout.view_state_error, null);
    }

    @Override
    protected View createEmptyView() {
        return View.inflate(getContext(), R.layout.view_state_empty, null);
    }
}
