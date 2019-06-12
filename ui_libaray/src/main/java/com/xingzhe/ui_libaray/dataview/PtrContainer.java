package com.xingzhe.ui_libaray.dataview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingzhe.ui_libaray.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by wumm on 2019/4/4.
 */
public class PtrContainer extends PtrFrameLayout {
    private PtrUIHandler ptrUIHandler;
    public PtrContainer(Context context) {
        this(context,null);
    }

    public PtrContainer(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PtrContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        ptrUIHandler = getPtrUIHandler();
        if (ptrUIHandler == null){
            ptrUIHandler = new DefaultPtrHandler(getContext());
        }
        setHeaderView((View) ptrUIHandler);
        addPtrUIHandler(ptrUIHandler);
    }

    private PtrUIHandler getPtrUIHandler() {
        return null;
    }

    public static class DefaultPtrHandler extends RelativeLayout implements PtrUIHandler {
        private ProgressBar progressBar;
        private TextView refreshTip;
        public DefaultPtrHandler(Context context) {
            this(context,null);
        }

        public DefaultPtrHandler(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public DefaultPtrHandler(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            View.inflate(context, R.layout.view_default_header,this);
            progressBar = findViewById(R.id.progress);
            refreshTip = findViewById(R.id.refresh_tips);

        }

        @Override
        public void onUIReset(PtrFrameLayout frame) {
            refreshTip.setText("下拉刷新");
        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {
            refreshTip.setText("释放刷新");
        }

        @Override
        public void onUIRefreshBegin(PtrFrameLayout frame) {
            refreshTip.setText("正在刷新");
        }

        @Override
        public void onUIRefreshComplete(PtrFrameLayout frame) {
            refreshTip.setText("刷新成功");
        }

        @Override
        public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        }
    }
}
