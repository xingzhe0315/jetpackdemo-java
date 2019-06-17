package com.xingzhe.ui_libaray.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by wumm on 2019/6/17.
 */
public abstract class PtrBaseHeaderView extends RelativeLayout implements PtrUIHandler {
    protected boolean isRefreshing;

    public PtrBaseHeaderView(Context context) {
        this(context, null);
    }

    public PtrBaseHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrBaseHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(createHeaderView(context));
    }

    abstract View createHeaderView(Context context);

    abstract void setNormalState();

    abstract void setReadyState();

    abstract void setSuccessState();

    abstract void setRefreshingState();

    abstract void onPositionMove(
            PtrFrameLayout frame,
            boolean isUnderTouch,
            byte status,
            PtrIndicator ptrIndicator
    );

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        setNormalState();
        isRefreshing = false;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        setRefreshingState();
        isRefreshing = true;
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        setSuccessState();
        isRefreshing = false;
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        onPositionMove(frame, isUnderTouch, status, ptrIndicator);
    }
}
