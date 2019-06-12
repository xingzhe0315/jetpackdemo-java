package com.xingzhe.jetpackdemo.multi;

import com.xingzhe.framework.data.BaseResponseData;
import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.BaseViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/3/25.
 */
public class MultiViewModel extends BaseViewModel<HomeData> {
    @Override
    public Observable<? extends BaseResponseData> getObservable() {
        Observable<AccountList> accountList = RetrofitClient.getRetrofit().create(ApiService.class).getAccountList();
//        Observable<ArticleList> articleList  = RetrofitClient.getRetrofit().create(ApiService.class).getArticalList("408",1);
        return accountList.<BaseResponseData>flatMap(accountList1 -> Observable.concat(Observable.just(accountList1),RetrofitClient.getRetrofit().create(ApiService.class).getArticalList(accountList1.getData().get(0).getId()+"",1)));
    }

    @Override
    protected HomeData getDataFromResponse(List<? extends BaseResponseData> responses) {
        HomeData homeData = new HomeData();
        homeData.accountList = ((AccountList) responses.get(0)).getData();
        homeData.articleList = ((ArticleList) responses.get(1)).getData().getDatas();
        return homeData;
    }


}
