package com.zhiao.develop.macaumagazine.interfaces.presenter;


import android.content.Context;

import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.Contants;
import com.zhiao.develop.macaumagazine.bean.News;
import com.zhiao.develop.macaumagazine.interfaces.model.NewsModelImpl;
import com.zhiao.develop.macaumagazine.interfaces.view.NewsView;
import com.zhiao.develop.macaumagazine.presenter.interfaces.NewsPresenter;

import java.util.HashMap;
import java.util.Map;

import cn.zhiao.baselib.net.StringTransactionListener;

/**
* Created by Administrator on 2017/04/13
*/

public class NewsPresenterImpl implements NewsPresenter {
    private NewsModelImpl model;
    private Context context;
    private NewsView newsView;

    public NewsPresenterImpl(Context context, NewsView newsView) {
        this.context = context;
        this.newsView = newsView;
        model = new NewsModelImpl(context);
    }

    @Override
    public void getNewsList(final String status, String cid, String tags, String pageId, String catchnum, String lang) {
        newsView.showProgress(context.getResources().getString(R.string.loading));
        Map<String, String> params = new HashMap<>();
        params.put("cid",cid);
        params.put("tags",tags);
        params.put("pageID",pageId);
        params.put("catchnum",catchnum);
        params.put("lang",lang);
        model.get(context, Contants.NEWS, params, new StringTransactionListener() {
            @Override
            public void onSuccess(String response) {
                newsView.logE(response);
                newsView.hideProgress();
                newsView.returnData(status,News.objectFromData(response),News.objectFromData(response).getContent());
            }
        });
    }
}