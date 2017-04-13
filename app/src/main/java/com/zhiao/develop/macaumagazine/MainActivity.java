package com.zhiao.develop.macaumagazine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhiao.develop.macaumagazine.adapter.NewsAdapter;
import com.zhiao.develop.macaumagazine.bean.News;
import com.zhiao.develop.macaumagazine.interfaces.presenter.NewsPresenterImpl;
import com.zhiao.develop.macaumagazine.interfaces.view.NewsView;
import com.zhiao.develop.macaumagazine.ui.MenuActivity;
import com.zhiao.develop.macaumagazine.ui.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseActivity;

public class MainActivity extends BaseActivity implements NewsView{

    private static final int REQUESECODE = 1000;
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    private NewsPresenterImpl presenter;
    private NewsAdapter adapter;
    private List<News.ContentBean> newses = new ArrayList<>();

    @Override
    public void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext());
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("aid", String.valueOf(newses.get(position)));
                gt(bundle, NewsDetailsActivity.class);
            }
        });
    }

    @Override
    public void initPresenter() {
        presenter = new NewsPresenterImpl(getContext(),this);
        presenter.getNewsList("998","23","1","10","zh");
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
                gtForResult(REQUESECODE,MenuActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void returnData(List<News.ContentBean> newses) {
        this.newses = newses;
        adapter.addAll(newses);
    }
}
