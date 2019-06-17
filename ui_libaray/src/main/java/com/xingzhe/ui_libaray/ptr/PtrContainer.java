package com.xingzhe.ui_libaray.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xingzhe.ui_libaray.DensityUtil;
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

    public static class DefaultPtrHandler extends PtrBaseHeaderView {
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
        }

        @Override
        View createHeaderView(Context context) {
            View view = View.inflate(context, R.layout.view_default_header,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context,60f)));
            progressBar = view.findViewById(R.id.progress);
            refreshTip = view.findViewById(R.id.refresh_tips);
            return view;
        }

        @Override
        void setNormalState() {
            refreshTip.setText("下拉刷新");
        }

        @Override
        void setReadyState() {
            refreshTip.setText("释放刷新");
        }

        @Override
        void setSuccessState() {
            refreshTip.setText("刷新成功");
        }

        @Override
        void setRefreshingState() {
            refreshTip.setText("正在刷新");

        }

        @Override
        void onPositionMove(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
            if (isRefreshing){
                return;
            }
            if (ptrIndicator!=null && ptrIndicator.getCurrentPercent() >= 1f){
                setReadyState();
            } else {
                setNormalState();
            }
        }

    }
}
