package com.xingzhe.ui_libaray.dataview;

/**
 * Created by wumm on 2019/5/6.
 */
public interface LoadMoreView {
    void setRetryHandler(DataRetryHandler retryHandler);

    void onLoading();

    void onError();

    void onEnd();
}
