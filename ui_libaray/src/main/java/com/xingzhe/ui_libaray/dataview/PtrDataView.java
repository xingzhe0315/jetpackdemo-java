package com.xingzhe.ui_libaray.dataview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xingzhe.framework.data.BaseObserver;
import com.xingzhe.ui_libaray.ptr.PtrContainer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wumm on 2019/4/4.
 */
public abstract class PtrDataView<Data,VM extends BaseViewModel<Data>> extends PtrContainer {

    private InnerDataView innerDataView;

    private boolean canRefresh = true;

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    public PtrDataView(Context context) {
        this(context,null);
    }

    public PtrDataView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PtrDataView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (!canRefresh)
                    return false;
                View scrollableContentView = getScrollableContentView(content);
                return PtrDefaultHandler.checkContentCanBePulledDown(frame,scrollableContentView,header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        innerDataView = new InnerDataView(context);
        addView(innerDataView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        onFinishInflate();
    }

    public View getScrollableContentView(View content){
        return innerDataView.getContentView()!=null?innerDataView.getContentView():content;
    }

    public void startLoad(){
        if (innerDataView != null) {
            innerDataView.startLoad();
        }
    }

    public void refresh(){
        if (innerDataView != null) {
            innerDataView.refresh();
        }
    }

    protected abstract VM createViewModel();

    protected abstract View createView();

    protected abstract void bindView(View view,Data data);

    public VM getViewModel(){
        return innerDataView.getViewModel();
    }

    public class InnerDataView extends SimpleDataView<Data,VM>{

        public InnerDataView(Context context) {
            super(context);
        }

        public InnerDataView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public InnerDataView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected VM createViewModel() {
            return PtrDataView.this.createViewModel();
        }

        @Override
        protected View createView() {
            return PtrDataView.this.createView();
        }

        @Override
        protected void bindView(View view, Data data) {
            PtrDataView.this.bindView(view,data);
        }

        @Override
        protected void onDataSuccess(Data data) {
            super.onDataSuccess(data);
            PtrDataView.this.refreshComplete();
        }

        @Override
        protected void onDataError(BaseObserver.ResponseError error) {
            super.onDataError(error);
            PtrDataView.this.refreshComplete();
        }
    }
}
