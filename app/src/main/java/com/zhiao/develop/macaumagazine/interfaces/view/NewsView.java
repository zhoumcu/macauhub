package com.zhiao.develop.macaumagazine.interfaces.view;

import com.zhiao.develop.macaumagazine.bean.News;

import java.util.List;

import cn.zhiao.baselib.base.IBaseView;

/**
* Created by Administrator on 2017/04/13
*/

public interface NewsView extends IBaseView{

    void returnData(String status, News news, List<News.ContentBean> newses);
}