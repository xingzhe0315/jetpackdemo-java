package com.xingzhe.ui_libaray.dataview;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.view.View;

import com.xingzhe.ui_libaray.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by wumm on 2019/5/5.
 */
public abstract class PtrHeaderListDataView<Data,VM extends BaseListViewModel<Data>> extends PtrListDataView<Data,VM> {
    public PtrHeaderListDataView(Context context) {
        super(context);
        getViewModel().getHeaderLiveData().observe((LifecycleOwner) context,this::onHeaderDataSuccess);
    }

    @Override
    protected CommonAdapter<Data> createAdapter() {
        CommonAdapter<Data> adapter = createListAdapter();
        for (int i = 0; i < getHeaderCount(); i++) {
            adapter.addHeaderView(createHeaderView(i));
        }
        return adapter;
    }

    protected abstract CommonAdapter<Data> createListAdapter();

    protected abstract int getHeaderCount();

    protected abstract View createHeaderView(int position);

    protected abstract void bindHeaderView(View view, int position,Object data);

    @Override
    protected void bindView(View view, List<Data> data) {
        super.bindView(view, data);
    }

    private void onHeaderDataSuccess(List<Object> headers){
        for (int i = 0; i < headers.size(); i++) {
            bindHeaderView(adapter.getHeaderByIndex(i),i,headers.get(i));
        }
    }
}
