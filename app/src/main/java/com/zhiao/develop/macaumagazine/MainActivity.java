package com.zhiao.develop.macaumagazine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhiao.develop.macaumagazine.adapter.NewsAdapter;
import com.zhiao.develop.macaumagazine.ui.MenuActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhiao.baselib.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.recycler)
    EasyRecyclerView recycler;

    @Override
    public void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new NewsAdapter(getContext()));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                gt(MenuActivity.class);
                //overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
