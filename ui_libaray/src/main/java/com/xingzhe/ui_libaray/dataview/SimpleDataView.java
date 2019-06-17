package com.xingzhe.ui_libaray.dataview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.xingzhe.framework.data.BaseObserver;

/**
 * Created by wumm on 2019/3/20.
 */
public abstract class SimpleDataView<Data,VM extends  BaseViewModel<Data>> extends RelativeLayout {

    private View contentView;
    private LoadingView loadingView;
    private VM viewModel;

    public SimpleDataView(Context context) {
        this(context, null);
    }

    public SimpleDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadingView = createLoadingView();
        loadingView.setRetryHandler(this::startLoad);
        addView(loadingView);
        viewModel = createViewModel();
        viewModel.getLiveData().observe((AppCompatActivity) context, this::onDataSuccess);
        viewModel.getErrorData().observe((AppCompatActivity) context,this::onDataError);
    }

    protected LoadingView createLoadingView() {
        return new DefaultLoadingView(getContext());
    }

    public void startLoad() {
        viewModel.loadData();
    }

    public void refresh(){
        viewModel.refresh();
    }

    protected abstract VM createViewModel();

    protected abstract View createView();

    protected abstract void bindView(View view, Data data);

    protected void onDataSuccess(Data data) {
        if (contentView == null) {
            contentView = createView();
            contentView.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            addView(contentView);
        }
        bindView(contentView, data);
        loadingView.setVisibility(GONE);
    }

    protected void onDataError(BaseObserver.ResponseError error) {
        loadingView.setState(LoadingView.STATE_ERROR);
    }

    public VM getViewModel() {
        return viewModel;
    }

    public View getContentView() {
        return contentView;
    }
}
