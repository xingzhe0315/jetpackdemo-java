package com.xingzhe.jetpackdemo.headerlist;

import com.xingzhe.framework.data.BaseResponseData;
import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.BaseListViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/5/6.
 */
public class HeaderListViewModel extends BaseListViewModel<ArticleList.Article.DatasBean> {
    private int id;

    @Override
    public Observable<? extends BaseResponseData> getObservable() {
        Observable<AccountList> accountList = RetrofitClient.getRetrofit().create(ApiService.class).getAccountList();
        return accountList.<BaseResponseData>flatMap(accountList1 -> {
            id = accountList1.getData().get(0).getId();
            return Observable.concat(Observable.just(accountList1), RetrofitClient.getRetrofit().create(ApiService.class).getArticalList(id + "", page));
        });
    }

    @Override
    protected List<ArticleList.Article.DatasBean> getListDataFromResponse(BaseResponseData response) {
        return ((ArticleList) response).getData().getDatas();
    }

    @Override
    protected Observable<? extends BaseResponseData> getLoadMoreObservable() {
        return RetrofitClient.getRetrofit().create(ApiService.class).getArticalList(id + "", page);
    }
}
