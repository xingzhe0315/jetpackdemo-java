package com.xingzhe.ui_libaray.dataview;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.xingzhe.framework.data.BaseObserver;
import com.xingzhe.framework.data.BaseResponseData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by wumm on 2019/3/20.
 */
public abstract class BaseViewModel<Data> extends ViewModel {
    private MutableLiveData<Data> liveData;
    private MutableLiveData<BaseObserver.ResponseError> errorData;


    public MutableLiveData<Data> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public MutableLiveData<BaseObserver.ResponseError> getErrorData() {
        if (errorData == null) {
            errorData = new MutableLiveData<>();
        }
        return errorData;
    }

    public abstract Observable<? extends BaseResponseData> getObservable();

    public void loadData() {
        getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponseData>() {
                    @Override
                    protected void onDataSuccess(List<BaseResponseData> response) {
                        getDataFromObserver(response);
                    }

                    @Override
                    protected boolean onDataError(ResponseError error) {
                        errorData.setValue(error);
                        return true;
                    }
                });
    }

    public void refresh(){
        loadData();
    }

    protected void getDataFromObserver(List<? extends BaseResponseData> responses){
        getLiveData().setValue(getDataFromResponse(responses));
    }

    protected Data getDataFromResponse(List<? extends BaseResponseData> responses){
        return (Data) responses.get(0).getData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
