package com.zhiao.develop.macaumagazine.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.Contants;
import com.zhiao.develop.macaumagazine.bean.News;
import com.zhiao.develop.macaumagazine.interfaces.presenter.NewsPresenterImpl;
import com.zhiao.develop.macaumagazine.interfaces.view.NewsView;
import com.zhiao.develop.macaumagazine.ui.NewsDetailsActivity;
import com.zhiao.develop.macaumagazine.vholder.NewsViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseListFragment;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;

/**
 * Created by ymn on 2017/4/16.
 */
public class HomeFragment extends BaseListFragment<News.ContentBean>  implements NewsView, SwipeRefreshLayout.OnRefreshListener{
    private static final int REQUESECODE = 1000;
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    private NewsPresenterImpl presenter;
    private List<News.ContentBean> newses = new ArrayList<>();
    private String tags = "23";
    private int pageId = 1;
    private News news;

    @Override
    public int getLayoutRes() {
        return R.layout.frg_home;
    }

    @Override
    public void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(getDataAdapter());
        getDataAdapter().setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("aid", String.valueOf(newses.get(position).getAID()));
                gt(bundle, NewsDetailsActivity.class);
            }
        });
        recycler.setRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void initPresenter() {
        presenter = new NewsPresenterImpl(getContext(),this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1002&&requestCode==REQUESECODE){
            tags = (String) data.getExtras().get("tags");
            onRefresh();
        }else if(resultCode==1003&&requestCode==REQUESECODE){
            onRefresh();
        }
    }
    @Override
    public void returnData(String status, News news, List<News.ContentBean> newses) {
        this.newses = newses;
        this.news = news;
        if(status.equals(Contants.REFREASH)){
            getDataAdapter().clear();
        }
        getDataAdapter().addAll(newses);
        getDataAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        presenter.getNewsList(Contants.REFREASH,"998",tags,"1",Contants.pageSize, SharedPrefrecesUtils.getStrFromSharedPrefrences("lang",getContext()));
    }
    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }
}
