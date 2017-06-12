package com.macauhub.macauhub.interfaces.presenter;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import cn.zhiao.baselib.base.BasePresenter;
import cn.zhiao.baselib.net.ResponseCode;
import cn.zhiao.baselib.net.StringTransactionListener;
import com.macauhub.macauhub.R;
import com.macauhub.macauhub.bean.Contants;
import com.macauhub.macauhub.bean.News;
import com.macauhub.macauhub.interfaces.model.NewsModelImpl;
import com.macauhub.macauhub.interfaces.view.NewsView;
import com.macauhub.macauhub.presenter.interfaces.NewsPresenter;

/**
* Created by Administrator on 2017/04/13
*/

public class NewsPresenterImpl extends BasePresenter implements NewsPresenter {
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
                if(status.equals(Contants.REFREASH)){
                    newsView.refreash(News.objectFromData(response),News.objectFromData(response).getContent());
                }else  if(status.equals(Contants.LOADMORE)){
                   newsView.loadMore(News.objectFromData(response),News.objectFromData(response).getContent());
                }
            }

            @Override
            public void onFailure(int errorCode) {
                super.onFailure(errorCode);
                newsView.hideProgress();
                switch (errorCode){
                    case ResponseCode.ERROR_NETWORK:
                        newsView.getRecycler().showError();
                        break;
                    case ResponseCode.ERROR_NETWORK_NOT_AVAILABLE:
                        newsView.getRecycler().showError();
                        break;
                }
            }
        });
    }
}