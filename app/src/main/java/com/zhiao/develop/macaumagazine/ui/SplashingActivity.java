package com.zhiao.develop.macaumagazine.ui;

import com.zhiao.develop.macaumagazine.MainActivity;
import com.zhiao.develop.macaumagazine.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.zhiao.baselib.base.BaseActivity;

/**
 * Created by ymn on 2017/4/12.
 */
public class SplashingActivity extends BaseActivity {

    @Override
    public void initView() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gt(MainActivity.class);
                finish();
            }
        },2000);
        switchLanguage();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_splash;
    }

}
