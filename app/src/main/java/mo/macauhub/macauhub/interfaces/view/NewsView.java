package mo.macauhub.macauhub.interfaces.view;

import java.util.List;

import cn.zhiao.baselib.base.IBaseListView;
import mo.macauhub.macauhub.bean.News;

/**
* Created by Administrator on 2017/04/13
*/

public interface NewsView<D> extends IBaseListView<D> {

    void refreash(News news,List<News.ContentBean> modle);

    void loadMore(News news,List<News.ContentBean> modle);

//    void returnData(String status, News news, List<News.ContentBean> newses);
}