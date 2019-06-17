package com.xingzhe.ui_libaray.dataview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xingzhe.ui_libaray.R;

/**
 * Created by wumm on 2019/5/6.
 */
public class DefaultLoadMoreView extends LinearLayout implements LoadMoreView {

    private ProgressBar progress;
    private TextView messageTv;

    private String message_loading = "加载中...";
    private String message_error = "网络错误，点击重试";
    private String message_end = "没有更多了";

    private DataRetryHandler retryHandler;

    public DefaultLoadMoreView(Context context) {
        this(context,null);
    }

    public DefaultLoadMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DefaultLoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_loading_more_footer,this);
        progress = findViewById(R.id.progress);
        messageTv = findViewById(R.id.message_tv);
    }

    @Override
    public void onLoading() {
        setOnClickListener(null);
        setClickable(false);
        progress.setVisibility(VISIBLE);
        messageTv.setText(message_loading);
    }

    @Override
    public void onError() {
        setClickable(true);
        progress.setVisibility(GONE);
        messageTv.setText(message_error);
        if (retryHandler!=null){
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLoading();
                    retryHandler.onHandleRetry();
                }
            });
        }
    }

    @Override
    public void onEnd() {
        setOnClickListener(null);
        setClickable(false);
        progress.setVisibility(GONE);
        messageTv.setText(message_end);
    }

    public void setRetryHandler(DataRetryHandler retryHandler) {
        this.retryHandler = retryHandler;
    }
}
