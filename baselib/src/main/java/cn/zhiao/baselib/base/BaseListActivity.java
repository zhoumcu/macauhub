package cn.zhiao.baselib.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.zhiao.baselib.R;
import cn.zhiao.baselib.utils.kit.Kits;

public  abstract class BaseListActivity<M> extends BaseActivity implements IBaseListView<BaseListActivity.DataAdapter>{

    private ListConfig mListConfig;
    private EasyRecyclerView mListView;

    @Override
    public EasyRecyclerView getRecycler() {
        return mListView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListConfig = getConfig();
        createRecycler();
        findRecycler();
        initList();
        if (mListConfig.mStartWithProgress) mListView.setAdapterWithProgress(mAdapter = getAdapter());
        else mListView.setAdapter(mAdapter = getAdapter());
        initAdapter();
        initListView();
//        initListPresenter();
    }
    @Override
    public void initView() {

    }

    /**
     * 初始化控件
     */
    public abstract void initListView();

    /**
     * 初始化控制中心
     */
//    public abstract void initListPresenter();

    @Override
    public void stopRefresh(){
        if(mListView!=null)
            mListView.getSwipeToRefresh().setRefreshing(false);
    }

    @Override
    public void showError(Throwable e){
        if (mListView!=null)
            mListView.showError();
    }

    public int getLayout(){
        return 0;
    }

    private void findRecycler(){
        mListView = (EasyRecyclerView) findViewById(R.id.recycler);
        if (mListView==null)throw new RuntimeException("No found recycler with id \"recycler\"");
        mListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createRecycler(){
        if(getLayoutRes()!=0) return;
        if (getLayout()!=0){
            setContentView(getLayout());
        }else if (mListConfig.mContainerLayoutRes!=0){
            setContentView(mListConfig.mContainerLayoutRes);
        }else if (mListConfig.mContainerLayoutView!=null){
            setContentView(mListConfig.mContainerLayoutView);
        }else{
            EasyRecyclerView mListView = new EasyRecyclerView(this);
            mListView.setId(R.id.recycler);
            mListView.setLayoutManager(new LinearLayoutManager(this));
            mListView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            setContentView(mListView);
        }
    }

    private void initList(){
        if (mListConfig.mRefreshAble)mListView.setRefreshListener(this);
        if (mListConfig.mContainerProgressAble){
            if (mListConfig.mContainerProgressView != null)mListView.setProgressView(mListConfig.mContainerProgressView);
            else mListView.setProgressView(mListConfig.mContainerProgressRes);
        }
        if (mListConfig.mContainerErrorAble){
            if (mListConfig.mContainerErrorView != null)mListView.setErrorView(mListConfig.mContainerErrorView);
            else mListView.setErrorView(mListConfig.mContainerErrorRes);
        }
        if (mListConfig.mContainerEmptyAble){
            if (mListConfig.mContainerEmptyView != null)mListView.setEmptyView(mListConfig.mContainerEmptyView);
            else mListView.setEmptyView(mListConfig.mContainerEmptyRes);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mListConfig.mPaddingNavigationBarAble && Kits.HardWare.hasSoftKeys(this)){
            mListView.setRecyclerPadding(0,0,0, Kits.ScreenUtils.getNavigationBarHeight(this));
        }
    }

    private void initAdapter() {
        if (mListConfig.mNoMoreAble) {
            if (mListConfig.mNoMoreView != null) mAdapter.setNoMore(mListConfig.mNoMoreView);
            else mAdapter.setNoMore(mListConfig.mNoMoreRes);
        }
        if (mListConfig.mLoadmoreAble) {
            if (mListConfig.mLoadMoreView != null)
                mAdapter.setMore(mListConfig.mLoadMoreView, this);
            else mAdapter.setMore(mListConfig.mLoadMoreRes, this);
        }
        if (mListConfig.mErrorAble) {
            View errorView;
            if (mListConfig.mErrorView != null)
                errorView = mAdapter.setMore(mListConfig.mErrorView, this);
            else errorView = mAdapter.setError(mListConfig.mErrorRes);
            if (mListConfig.mErrorTouchToResumeAble)
                errorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.resumeMore();
                    }
                });
        }
    }

    public ListConfig getConfig(){
        return ListConfig.Default.clone();
    }

    public int getViewType1(int type){
        return 0;
    }

    private DataAdapter mAdapter;

    @Override
    public BaseListActivity.DataAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter = new BaseListActivity.DataAdapter(getContext());
        return mAdapter;
    }

    public abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    public class DataAdapter extends RecyclerArrayAdapter<M> {

        public DataAdapter(Context context) {
            super(context);
        }

        public DataAdapter(Context context,List<M> data) {
            super(context,data);
        }

        @Override
        public int getViewType(int position) {
            return getViewType1(position);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }
    }
}
