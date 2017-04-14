package com.zhiao.develop.macaumagazine.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.Contants;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.baselib.webViewUtils.ProgressWebView;

/**
 * Created by ymn on 2017/4/12.
 */
public class NewsDetailsActivity extends BaseActivity {
    @Bind(R.id.webView)
    ProgressWebView webView;
    private String aid;

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Contants.DETAILS+"aid="+aid+"&lang="+ SharedPrefrecesUtils.getStrFromSharedPrefrences("lang",getContext()));
    }

    @Override
    public void initPresenter() {
        Bundle bundle = getIntent().getExtras();
        aid = bundle.getString("aid");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_details;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                //gt(SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
