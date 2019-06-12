package com.xingzhe.jetpackdemo.service;


import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wumm on 2019/3/14.
 */
public interface ApiService {
    @GET("wxarticle/chapters/json")
    Observable<AccountList> getAccountList();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<ArticleList> getArticalList(@Path("id") String accountId, @Path("page") int page);
}
