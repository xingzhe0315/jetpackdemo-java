package com.xingzhe.jetpackdemo.normal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.xingzhe.jetpackdemo.service.bean.AccountList;
import com.xingzhe.ui_libaray.ViewModelCreator;
import com.xingzhe.ui_libaray.dataview.SimpleDataView;

import java.util.List;

public class NormalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NormalView normalView = new NormalView(this);
        setContentView(normalView);
        normalView.startLoad();
    }

    class NormalView extends SimpleDataView<List<AccountList.Account>,NormalViewModel>{

        public NormalView(Context context) {
            this(context,null);
        }

        public NormalView(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public NormalView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected NormalViewModel createViewModel() {
            return ViewModelCreator.createViewModel(getContext(),NormalViewModel.class);
        }

        @Override
        protected View createView() {
            TextView textView = new TextView(getContext());
            return textView;
        }

        @Override
        protected void bindView(View view, List<AccountList.Account> accountList) {
            StringBuilder accounts = new StringBuilder();
            for (AccountList.Account account:accountList){
                accounts.append(account.getName()).append("\n");
            }
            ((TextView) view).setText(accounts.toString());
        }
    }


}
