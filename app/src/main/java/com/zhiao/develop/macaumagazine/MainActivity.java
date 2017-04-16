package com.zhiao.develop.macaumagazine;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.zhiao.develop.macaumagazine.ui.fragment.HomeFragment;
import com.zhiao.develop.macaumagazine.ui.fragment.MenuFragment;
import com.zhiao.develop.macaumagazine.ui.fragment.SettingFragment;

import cn.zhiao.baselib.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final int REQUESECODE = 1000;
    private boolean isMenu = true;
    private boolean isClose = false;
    private boolean isSetting = false;
    private boolean isShare = false;

    @Override
    public void initView() {
        changeActionBar(0);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.getItem(0).setVisible(isMenu);
        menu.getItem(1).setVisible(isClose);
        menu.getItem(2).setVisible(isSetting);
        menu.getItem(3).setVisible(isShare);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                changeActionBar(1);
                MenuFragment.jumpIn((AppCompatActivity) getContext());
                break;
            case R.id.action_setting:
                changeActionBar(2);
                SettingFragment.jumpIn((AppCompatActivity) getContext());
                break;
            case R.id.action_close:
                changeActionBar(1);
                getSupportFragmentManager().popBackStack();
                break;
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeActionBar(int style) {
        ActionBar actionBar = getSupportActionBar();
        switch (style){
            case 0:
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setLogo(R.mipmap.alogo);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                addFragment(R.id.content_container,new HomeFragment());
                break;
            case 1:
                isMenu = false;
                isSetting = true;
                isClose = false;
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setLogo(R.mipmap.alogo);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
                break;
            case 2:
                isMenu = false;
                isSetting = false;
                isClose = true;
                actionBar.setTitle(R.string.setting);
                break;
        }
        invalidateOptionsMenu();
    }
}
