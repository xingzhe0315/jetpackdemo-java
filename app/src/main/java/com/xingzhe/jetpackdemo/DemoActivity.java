package com.xingzhe.jetpackdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xingzhe.jetpackdemo.headerlist.HeaderListActivity;
import com.xingzhe.jetpackdemo.multi.MultiDataActivity;
import com.xingzhe.jetpackdemo.normal.NormalActivity;
import com.xingzhe.jetpackdemo.normalList.NormalListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.four)
    TextView four;
    @BindView(R.id.five)
    TextView five;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.one, R.id.two, R.id.three, R.id.four, R.id.five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one:
                startActivity(new Intent(DemoActivity.this, NormalActivity.class));
                break;
            case R.id.two:
                startActivity(new Intent(DemoActivity.this, NormalListActivity.class));
                break;
            case R.id.three:
                startActivity(new Intent(DemoActivity.this, MultiDataActivity.class));
                break;
            case R.id.four:
                startActivity(new Intent(DemoActivity.this, HeaderListActivity.class));
                break;
            case R.id.five:
                startActivity(new Intent(DemoActivity.this, MainActivity.class));
                break;
        }
    }
}
