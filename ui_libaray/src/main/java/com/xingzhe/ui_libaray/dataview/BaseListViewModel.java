package com.xingzhe.ui_libaray.dataview;

import android.arch.lifecycle.MutableLiveData;

import com.xingzhe.framework.data.BaseObserver;
import com.xingzhe.framework.data.BaseResponseData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by wumm on 2019/5/5.
 */
public abstract class BaseListViewModel<Data> extends BaseViewModel<List<Data>> {
    private MutableLiveData<List<Object>> headerDatas;

    private MutableLiveData<BaseObserver.ResponseError> loadMoreError;

    private MutableLiveData<List<Data>> appendDataList;

    protected int page = 1;

    public void setPage(int page) {
        this.page = page;
    }

    MutableLiveData<List<Object>> getHeaderLiveData() {
        if (headerDatas == null) {
            headerDatas = new MutableLiveData<>();
        }
        return headerDatas;
    }

    MutableLiveData<BaseObserver.ResponseError> getLoadMoreErrorLiveData() {
        if (loadMoreError == null) {
            loadMoreError = new MutableLiveData<>();
        }
        return loadMoreError;
    }

    MutableLiveData<List<Data>> getAppendDataLiveData() {
        if (appendDataList == null) {
            appendDataList = new MutableLiveData<>();
        }
        return appendDataList;
    }

    @Override
    protected void getDataFromObserver(List<? extends BaseResponseData> responses) {
        page++;
        getLiveData().setValue(getListDataFromResponse(responses.get(responses.size() - 1)));
        getHeaderLiveData().setValue(getHeaderDataFromResponse(responses.subList(0, responses.size() - 1)));
    }

    @Override
    public void refresh() {
        page = 1;
        super.refresh();
    }

    protected List<Object> getHeaderDataFromResponse(List<? extends BaseResponseData> responses) {
        List<Object> headers = new ArrayList<>();
        for (BaseResponseData response : responses) {
            headers.add(response.getData());
        }
        return headers;
    }

    protected List<Data> getListDataFromResponse(BaseResponseData response) {
        return (List<Data>) response.getData();
    }

    protected abstract Observable<? extends BaseResponseData> getLoadMoreObservable();

    public void loadMore() {
        getLoadMoreObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<BaseResponseData>() {
            @Override
            protected void onDataSuccess(List<BaseResponseData> response) {
                appendMoreData(response.get(0));
                page++;
            }

            @Override
            protected boolean onDataError(ResponseError error) {
                getLoadMoreErrorLiveData().setValue(error);
                return true;
            }
        });
    }

    protected void appendMoreData(BaseResponseData response) {
        getAppendDataLiveData().setValue(getListDataFromResponse(response));
    }

}
