package com.xingzhe.ui_libaray.dataview;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xingzhe.framework.data.BaseObserver;
import com.xingzhe.ui_libaray.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by wumm on 2019/3/25.
 */
public abstract class PtrListDataView<Data, VM extends BaseListViewModel<Data>> extends PtrDataView<List<Data>, VM> {

    protected CommonAdapter<Data> adapter;
    private RecyclerView recyclerView;

    public PtrListDataView(Context context) {
        super(context);
        getViewModel().getAppendDataLiveData().observe((LifecycleOwner) context,this::appendData);
        getViewModel().getLoadMoreErrorLiveData().observe((LifecycleOwner) context,this::onLoadMoreError);
    }


    private LoadMoreView loadMoreView;

    private boolean hasMore = true;

    private boolean canLoadMore = true;

    @Override
    protected View createView() {

        recyclerView = new RecyclerView(getContext());
        adapter = createAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LoadMoreView loadMoreView = createLoadMoreView();
        if (loadMoreView == null) {
            loadMoreView = new DefaultLoadMoreView(getContext());
        }
        ((View) loadMoreView).setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        loadMoreView.setRetryHandler(new DataRetryHandler() {
            @Override
            public void onHandleRetry() {
                loadMore();
            }
        });
        this.loadMoreView = loadMoreView;
        adapter.addFooterView((View) loadMoreView);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                if (view instanceof LoadMoreView) {
                    if (hasMore && canLoadMore && !isRefreshing()) {
                        loadMore();
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });

        return recyclerView;
    }

    private LoadMoreView createLoadMoreView() {
        return null;
    }

    private void loadMore() {
        loadMoreView.onLoading();
        getViewModel().loadMore();
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    protected void bindView(View view, List<Data> data) {
        adapter.setData(data);
        hasMore = true;
    }

    protected abstract CommonAdapter<Data> createAdapter();

    protected LoadMoreView craeteLoadMoreFooter() {
        return null;
    }

    private void onLoadMoreError(BaseObserver.ResponseError error) {
        loadMoreView.onError();
    }

    private void appendData(List<Data> data) {
        if (data == null || data.size() == 0){
            hasMore = false;
            loadMoreView.onEnd();
            return;
        }
        adapter.dataAppendAndNotify(data);
    }

    private void asList(){

    }

    private void asGrid(int column){

    }
}
