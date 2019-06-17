package com.xingzhe.jetpackdemo.multi;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.framework.data.SingleObserver;
import com.xingzhe.jetpackdemo.MainListView;
import com.xingzhe.jetpackdemo.R;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.dataview.SimpleDataView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MultiDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MultiDataView(this));
    }

    public class MultiDataView extends SimpleDataView<HomeData,MultiViewModel> {

        private TabLayout tabLayout;
        private RecyclerView recyclerView;
        private MainListView.AccountAdapter adapter;

        public MultiDataView(Context context) {
            super(context);
            startLoad();
        }

        @Override
        protected MultiViewModel createViewModel() {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MultiViewModel.class);
        }

        @Override
        protected View createView() {
            View view = View.inflate(getContext(),R.layout.activity_multi_data,null);
            tabLayout = ((TabLayout) view.findViewById(R.id.tab_layout));
            recyclerView = ((RecyclerView) view.findViewById(R.id.recycler_view));
            adapter = new MainListView.AccountAdapter(getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    AccountList.Account account = (AccountList.Account) tab.getTag();
                    RetrofitClient.getRetrofit().create(ApiService.class).getArticalList(account.getId()+"",1)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<ArticleList>() {
                                @Override
                                protected boolean onDataError(ResponseError error) {
                                    return false;
                                }

                                @Override
                                protected void onDataSuccess(ArticleList response) {
                                    adapter.setData(response.getData().getDatas());
                                }
                            });
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            return view;
        }

        @Override
        protected void bindView(View view, HomeData homeData) {
            List<AccountList.Account> accountList = homeData.accountList;
            for (AccountList.Account account : accountList) {
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(account.getName());
                tab.setTag(account);
                tabLayout.addTab(tab);
            }
            adapter.setData(homeData.articleList);
        }
    }
}
