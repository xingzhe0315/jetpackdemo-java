package com.xingzhe.framework.data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wumm on 2019/3/22.
 */
public abstract class BaseObserver<Data extends BaseResponseData> implements Observer<Data> {
    private Disposable disposable;
    private List<Data> dataList;
//
//    private Context context;
//    private String loadingMessage;
//
//    public BaseObserver(Context context, String loadingMessage) {
//        this.context = context;
//        this.loadingMessage = loadingMessage;
//    }
//
//    public void showLoading(Context context, String message){
//        this.context = context;
//        this.loadingMessage = message;
//    }

    @Override
    public void onSubscribe(Disposable d) {
        dataList = new ArrayList<>();
        disposable = d;
    }

    @Override
    public void onNext(Data response) {
        if (response != null) {
            if (response.getErrorCode() == 0) {
                dataList.add(response);
            } else {
                dataList.clear();
                dataList = null;
                if (!onDataError(new ResponseError(response.getErrorCode(), response.getErrorMsg()))) {
                    NotifyUtil.notifyMessage(response.getErrorMsg());
                }
                onError(null);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        if (e != null && !onDataError(new ResponseError(-100, e.getMessage()))) {
            NotifyUtil.notifyMessage("网络错误");
        }
    }

    @Override
    public void onComplete() {
        onDataSuccess(dataList);
        disposable.dispose();
    }

    protected abstract void onDataSuccess(List<Data> response);

    protected abstract boolean onDataError(ResponseError error);

    public class ResponseError {
        int errorCode;
        String errorMessage;

        ResponseError(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

}
