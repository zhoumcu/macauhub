package cn.zhiao.baselib.base;

import android.support.v4.widget.SwipeRefreshLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 公共View接口
 *
 * @author Ht
 */
public interface IBaseListView<D> extends IBaseView,
        SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener{

    //public void setAdapter(M bean);

    public void stopRefresh();

    public void showError(Throwable e);


    D getAdapter();

    EasyRecyclerView getRecycler();
}
