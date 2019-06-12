package com.xingzhe.jetpackdemo;

import com.xingzhe.framework.data.BaseResponseData;
import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.BaseListViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wumm on 2019/3/21.
 */
public class MainListViewModel extends BaseListViewModel<ArticleList.Article.DatasBean> {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected Observable<? extends BaseResponseData> getLoadMoreObservable() {
        return getObservable();
    }

    @Override
    public Observable<? extends BaseResponseData> getObservable() {
        return RetrofitClient.getRetrofit().create(ApiService.class).getArticalList(id,page);
    }

    @Override
    protected List<ArticleList.Article.DatasBean> getListDataFromResponse(BaseResponseData response) {
        return ((ArticleList) response).getData().getDatas();
    }
}
