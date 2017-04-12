package com.zhiao.develop.macaumagazine.ui;

import android.view.Menu;
import android.view.MenuItem;

import com.zhiao.develop.macaumagazine.R;

import cn.zhiao.baselib.base.BaseActivity;

/**
 * Created by ymn on 2017/4/12.
 */
public class SettingActivity extends BaseActivity{
    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_setting;
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
