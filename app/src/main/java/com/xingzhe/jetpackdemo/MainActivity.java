package com.xingzhe.jetpackdemo;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.xingzhe.framework.data.RetrofitClient;
import com.xingzhe.framework.data.SingleObserver;
import com.xingzhe.jetpackdemo.headerlist.HeaderListActivity;
import com.xingzhe.jetpackdemo.multi.MultiDataActivity;
import com.xingzhe.jetpackdemo.service.ApiService;
import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.ui_libaray.dataview.SimpleDataView;
import com.xingzhe.ui_libaray.viewpager.BasePagerAdapter;
import com.xingzhe.ui_libaray.viewpager.XZViewPager;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainView mainView = new MainView(this);
        setContentView(mainView);
        mainView.startLoad();

        RetrofitClient.getRetrofit().create(ApiService.class).getAccountList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AccountList>() {
                    @Override
                    protected boolean onDataError(ResponseError error) {
                        return false;
                    }

                    @Override
                    protected void onDataSuccess(AccountList response) {
                        Log.e("singleObserver","---"+new Gson().toJson(response));
                    }
                });

    }

    public class MainView extends SimpleDataView<List<AccountList.Account>,MainViewModel> {
        private List<AccountList.Account> accounts;
        private MainPagerAdapter pagerAdapter;
        private TabLayout tabLayout;

        public MainView(Context context) {
            super(context);
        }

        @Override
        protected MainViewModel createViewModel() {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        }


        @Override
        protected View createView() {
            View view = View.inflate(getContext(), R.layout.view_main, null);
            XZViewPager viewPager = view.findViewById(R.id.view_pager);
            tabLayout = view.findViewById(R.id.tab_layout);
            pagerAdapter = new MainPagerAdapter();
            viewPager.setAdapter(pagerAdapter);
            viewPager.showPage(0);
            tabLayout.setupWithViewPager(viewPager);
            Button button = view.findViewById(R.id.button);
            button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MultiDataActivity.class)));
//            button.setOnClickListener(v -> openFile());
//            button.setOnClickListener(v -> openHeaderList());
            view.findViewById(R.id.header_list).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HeaderListActivity.class)));
            return view;
        }

        private void openFile(){
            File file = new File(getExternalFilesDir(null),"documents/test.doc");
            Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.xingzhe,provider", file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivity(intent);
        }

        @Override
        protected void bindView(View view, List<AccountList.Account> accounts) {
            pagerAdapter.setAccounts(accounts);
        }
    }

    public class MainPagerAdapter extends BasePagerAdapter {
        private List<AccountList.Account> accounts;

        public void setAccounts(List<AccountList.Account> accounts) {
            this.accounts = accounts;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return accounts == null ? 0 : accounts.size();
        }

        @Override
        protected View createItemView(Context context, int position) {
            MainListView mainListView = new MainListView(context);
            mainListView.setAccountId(accounts.get(position).getId() + "");
            mainListView.setPage(1);
            return mainListView;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return accounts.get(position).getName();
        }
    }

}
