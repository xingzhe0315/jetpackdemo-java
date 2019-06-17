package com.xingzhe.jetpackdemo.normal;

import com.xingzhe.framework.data.BaseResponseData;
import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.ui_libaray.dataview.BaseViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/6/13.
 */
public class NormalViewModel extends BaseViewModel<List<AccountList.Account>> {

    @Override
    public Observable<? extends BaseResponseData> getObservable() {
        return RetrofitClient.getRetrofit().create(ApiService.class).getAccountList();
    }
}
