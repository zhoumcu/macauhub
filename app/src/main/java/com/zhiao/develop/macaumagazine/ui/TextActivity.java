package com.zhiao.develop.macaumagazine.ui;

import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.Contants;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseActivity;

/**
 * Created by ymn on 2017/4/12.
 */
public class TextActivity extends BaseActivity {

    @Bind(R.id.tv_text)
    TextView tvText;

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.alogo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
        tvText.setText( getIntent().getStringExtra(Contants.TEXT));
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_text;
    }

}
