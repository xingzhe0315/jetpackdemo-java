package com.xingzhe.jetpackdemo.normalList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xingzhe.jetpackdemo.R;
import com.xingzhe.jetpackdemo.service.bean.ArticleList;
import com.xingzhe.ui_libaray.ViewModelCreator;
import com.xingzhe.ui_libaray.dataview.PtrListDataView;
import com.xingzhe.ui_libaray.recyclerview.CommonAdapter;
import com.xingzhe.ui_libaray.recyclerview.CommonViewHolder;

public class NormalListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NormalListView normalListView = new NormalListView(this);
        setContentView(normalListView);
        normalListView.startLoad();
    }

    public class NormalListView extends PtrListDataView<ArticleList.Article.DatasBean,NormalListViewModel>{

        public NormalListView(Context context) {
            super(context);
        }

        @Override
        protected CommonAdapter<ArticleList.Article.DatasBean> createAdapter() {
            return new ArticleListAdapter(getContext());
        }

        @Override
        protected NormalListViewModel createViewModel() {
            return ViewModelCreator.createViewModel(getContext(),NormalListViewModel.class);
        }
    }

    public static class ArticleListAdapter extends CommonAdapter<ArticleList.Article.DatasBean> {
        public ArticleListAdapter(Context context) {
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
