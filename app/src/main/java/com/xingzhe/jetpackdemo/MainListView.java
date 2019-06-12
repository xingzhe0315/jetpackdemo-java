package com.xingzhe.jetpackdemo;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.view.View;

import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.PtrListDataView;
import com.xingzhe.ui_libaray.recyclerview.CommonAdapter;
import com.xingzhe.ui_libaray.recyclerview.CommonViewHolder;
import com.xingzhe.ui_libaray.viewpager.Page;

/**
 * Created by wumm on 2019/3/21.
 */
public class MainListView extends PtrListDataView<ArticleList.Article.DatasBean,MainListViewModel> implements Page {
    private String id;
    private int page;

    public MainListView(Context context) {
        super(context);
    }

    public void setAccountId(String id) {
        this.id = id;
        (getViewModel()).setId(id);
    }

    public void setPage(int page) {
        this.page = page;
        (getViewModel()).setPage(page);
    }

    @Override
    protected MainListViewModel createViewModel() {
        return ViewModelProvider.AndroidViewModelFactory.getInstance((Application) getContext().getApplicationContext()).create(MainListViewModel.class);
    }
    @Override
    protected CommonAdapter<ArticleList.Article.DatasBean> createAdapter() {
        return new AccountAdapter(getContext());
    }

    @Override
    public void onPageInit() {
        startLoad();
    }

    @Override
    public void onPageShow() {

    }

    @Override
    public void onPageHide() {

    }

    public static class AccountAdapter extends CommonAdapter<ArticleList.Article.DatasBean> {
        public AccountAdapter(Context context) {
            super(context);
        }

        @Override
        protected View getItemView() {
            return View.inflate(context, R.layout.view_account_item, null);
        }

        @Override
        protected void bindItemView(CommonViewHolder commonViewHolder, int position, ArticleList.Article.DatasBean article) {
            commonViewHolder.setText(R.id.account_name, article.getTitle());
        }

    }
}
