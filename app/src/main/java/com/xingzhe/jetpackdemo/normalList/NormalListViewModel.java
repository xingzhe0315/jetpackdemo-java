package com.xingzhe.jetpackdemo.normalList;

import com.xingzhe.framework.data.BaseResponseData;
import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.BaseListViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/6/13.
 */
public class NormalListViewModel extends BaseListViewModel<ArticleList.Article.DatasBean> {
    @Override
    protected Observable<? extends BaseResponseData> getLoadMoreObservable() {
        return getObservable();
    }

    @Override
    public Observable<? extends BaseResponseData> getObservable() {
        return RetrofitClient.getRetrofit().create(ApiService.class).getArticalList(String.valueOf(408),page);
    }

    @Override
    protected List<ArticleList.Article.DatasBean> getListDataFromResponse(BaseResponseData response) {
        return ((ArticleList) response).getData().getDatas();
    }
}
