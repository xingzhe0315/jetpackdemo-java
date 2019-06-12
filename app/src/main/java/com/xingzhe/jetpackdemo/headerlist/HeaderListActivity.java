package com.xingzhe.jetpackdemo.headerlist;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xingzhe.jetpackdemo.MainListView;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.PtrHeaderListDataView;
import com.xingzhe.ui_libaray.recyclerview.CommonAdapter;

import java.util.List;

public class HeaderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new HeaderListView(this));
    }

    public class HeaderListView extends PtrHeaderListDataView<ArticleList.Article.DatasBean, HeaderListViewModel> {

        public HeaderListView(Context context) {
            super(context);
            startLoad();
        }

        @Override
        protected HeaderListViewModel createViewModel() {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(HeaderListViewModel.class);
        }

        @Override
        protected CommonAdapter<ArticleList.Article.DatasBean> createListAdapter() {
            return new MainListView.AccountAdapter(getContext());
        }

        @Override
        protected int getHeaderCount() {
            return 1;
        }

        @Override
        protected View createHeaderView(int position) {
            TabLayout tabLayout = new TabLayout(getContext());
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            return tabLayout;
        }

        @Override
        protected void bindHeaderView(View view, int position, Object data) {
            List<AccountList.Account> accountList = (List<AccountList.Account>) data;
            TabLayout tabLayout = ((TabLayout) view);
            for (AccountList.Account account : accountList) {
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(account.getName());
                tabLayout.addTab(tab);
            }
        }

    }
}
