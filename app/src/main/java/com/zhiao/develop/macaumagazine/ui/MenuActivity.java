package com.zhiao.develop.macaumagazine.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhiao.develop.macaumagazine.MainActivity;
import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.adapter.CategoryAdapter;
import com.zhiao.develop.macaumagazine.bean.Categorys;
import com.zhiao.develop.macaumagazine.bean.Contants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.custom.weight.FullyLinearLayoutManager;

/**
 * Created by ymn on 2017/4/12.
 */
public class MenuActivity extends BaseActivity {
    @Bind(R.id.map)
    ImageView map;
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    private CategoryAdapter adapter;
    private List<Categorys> categoryses = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.alogo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
        recycler.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        for (int i = 0; i < Contants.categoryName.length; i++) {
            Categorys categorys = new Categorys();
            categorys.setImageUrl(Contants.imageUrl[i]);
            categorys.setAtitle(Contants.categoryName[i]);
            categoryses.add(categorys);
        }
        adapter = new CategoryAdapter(getContext(), categoryses);
        //recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                setResult(1002, new Intent(getContext(), MainActivity.class));
                finish();
            }
        });
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

    @OnClick(R.id.map)
    public void onClick() {
    }
}
