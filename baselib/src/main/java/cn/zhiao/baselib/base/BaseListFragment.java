package cn.zhiao.baselib.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.zhiao.baselib.R;
import cn.zhiao.baselib.utils.kit.Kits;

public abstract class BaseListFragment<M> extends BaseFragment implements IBaseListView<BaseListFragment.DataAdapter> {

    private ListConfig mListConfig;
    private EasyRecyclerView mListView;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListConfig = getConfig();
        createRecycler(container);
        findRecycler();
        initList();
        if (mListConfig.mStartWithProgress) mListView.setAdapterWithProgress(mAdapter = getAdapter());
        else mListView.setAdapter(mAdapter = getAdapter());
        initAdapter();
        return mRootView;
    }

    public void stopRefresh(){
        if(mListView!=null)
            mListView.getSwipeToRefresh().setRefreshing(false);
    }
    public void showError(Throwable e){
        if (mListView!=null)
            mListView.showError();
    }

    public int getLayout(){
        return 0;
    }

    private void findRecycler(){
        mListView = (EasyRecyclerView) mRootView.findViewById(R.id.recycler);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void createRecycler(ViewGroup container){
        if(getLayoutRes()!=0) return;
        if (getLayout()!=0){
            mRootView = LayoutInflater.from(getActivity()).inflate(getLayout(), container, false);
        }else if (mListConfig.mContainerLayoutRes!=0){
            mRootView = LayoutInflater.from(getActivity()).inflate(mListConfig.mContainerLayoutRes,container,false);
        }else if (mListConfig.mContainerLayoutView!=null){
            mRootView = mListConfig.mContainerLayoutView;
        }else{
            EasyRecyclerView mListView = new EasyRecyclerView(getActivity());
            mListView.setId(R.id.recycler);
            mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mListView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mRootView = mListView;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mListConfig.mPaddingNavigationBarAble && Kits.HardWare.hasSoftKeys(getContext())){
            mListView.setRecyclerPadding(0,0,0, Kits.ScreenUtils.getNavigationBarHeight(getContext()));
        }
    }

    private void initAdapter(){
        if (mListConfig.mNoMoreAble){
            if (mListConfig.mNoMoreView != null)mAdapter.setNoMore(mListConfig.mNoMoreView);
            else mAdapter.setNoMore(mListConfig.mNoMoreRes);
        }
        if (mListConfig.mLoadmoreAble){
            if (mListConfig.mLoadMoreView != null)mAdapter.setMore(mListConfig.mLoadMoreView, this);
            else mAdapter.setMore(mListConfig.mLoadMoreRes, this);
        }
        if (mListConfig.mErrorAble){
            View errorView;
            if (mListConfig.mErrorView != null)errorView = mAdapter.setError(mListConfig.mErrorView);
            else errorView = mAdapter.setError(mListConfig.mErrorRes);
            if (mListConfig.mErrorTouchToResumeAble)errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.resumeMore();
                }
            });
        }
    }

    private BaseListFragment.DataAdapter mAdapter;

    public BaseListFragment.DataAdapter getAdapter(){
        if(mAdapter==null)
            mAdapter = new BaseListFragment.DataAdapter(getContext());
        return mAdapter;
    }

    public ListConfig getConfig(){
        return ListConfig.Default.clone();
    }

    public int getViewType1(int type){
        return 0;
    }

    public abstract BaseViewHolder<M> getViewHolder(ViewGroup parent, int viewType);

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
