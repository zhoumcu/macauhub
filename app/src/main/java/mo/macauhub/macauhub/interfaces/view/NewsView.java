package mo.macauhub.macauhub.interfaces.view;

import mo.macauhub.macauhub.bean.News;

import java.util.List;

import cn.zhiao.baselib.base.IBaseView;

/**
* Created by Administrator on 2017/04/13
*/

public interface NewsView extends IBaseView{

    void returnData(String status, News news, List<News.ContentBean> newses);
}