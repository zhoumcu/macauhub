package com.zhiao.develop.macaumagazine.ui;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.zhiao.develop.macaumagazine.R;

import cn.zhiao.baselib.base.BaseActivity;

/**
 * Created by ymn on 2017/4/12.
 */
public class MenuActivity extends BaseActivity{
    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.alogo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_menu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                gt(SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
