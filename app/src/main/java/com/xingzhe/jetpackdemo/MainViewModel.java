package com.xingzhe.jetpackdemo;


import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.ui_libaray.dataview.BaseViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/3/19.
 */
public class MainViewModel extends BaseViewModel<List<AccountList.Account>> {

    @Override
    public Observable<AccountList> getObservable() {
        return RetrofitClient.getRetrofit().create(ApiService.class).getAccountList();
    }
}
