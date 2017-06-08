package mo.macauhub.macauhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseListActivity;
import cn.zhiao.baselib.base.ListConfig;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import mo.macauhub.macauhub.bean.Contants;
import mo.macauhub.macauhub.bean.News;
import mo.macauhub.macauhub.interfaces.presenter.NewsPresenterImpl;
import mo.macauhub.macauhub.interfaces.view.NewsView;
import mo.macauhub.macauhub.ui.MenuActivity;
import mo.macauhub.macauhub.ui.NewsDetailsActivity;
import mo.macauhub.macauhub.ui.SettingActivity;
import mo.macauhub.macauhub.vholder.NewsViewHolder;

public class MainActivity extends BaseListActivity<News.ContentBean> implements NewsView<BaseListActivity.DataAdapter> {

    private static final int REQUESECODE = 1000;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private NewsPresenterImpl presenter;
    private List<News.ContentBean> newses = new ArrayList<>();
    private String tags = "0";
    private int pageId = 1;
    private News news;

    @Override
    public void initListView() {
        if(tags.equals(Contants.DEFAULT)){
            toolbar.setNavigationIcon(R.mipmap.setting);
        }else {
            toolbar.setNavigationIcon(R.mipmap.arrow);
        }
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(false); //设置返回键可用
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setLogo(R.mipmap.alogo);
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
        getAdapter().setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("aid", newses.get(position));
                gt(bundle, NewsDetailsActivity.class);
            }
        });
    }

    @Override
    public ListConfig getConfig() {
        return super.getConfig()
                .setLoadmoreAble(true)
                .setRefreshAble(true)
                .setNoMoreAble(true)
                .setErrorAble(true)
                .setStartWithProgress(true)
                .setErrorTouchToResumeAble(true);
    }

    @Override
    public void initListPresenter() {
        presenter = new NewsPresenterImpl(getContext(), this);
        onRefresh();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SharedPrefrecesUtils.getStrFromSharedPrefrences("lang", getContext()).equals(SharedPrefrecesUtils.getStrFromSharedPrefrences("currlang", getContext()))) {
            SharedPrefrecesUtils.saveStrToSharedPrefrences("currlang", SharedPrefrecesUtils.getStrFromSharedPrefrences("lang", getContext()), getContext());
            finish();
            gt(MainActivity.class);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                gtForResult(REQUESECODE, MenuActivity.class);
                break;
            case android.R.id.home:
                if(tags.equals(Contants.DEFAULT)){
                    gt(SettingActivity.class);
                }else {
                    tags = Contants.DEFAULT;
                    toolbar.setNavigationIcon(R.mipmap.setting);
                    setSupportActionBar(toolbar);
                    onRefresh();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002 && requestCode == REQUESECODE) {
            tags = (String) data.getExtras().get("tags");
            if(!tags.equals(Contants.DEFAULT)){
                toolbar.setNavigationIcon(R.mipmap.arrow);
                setSupportActionBar(toolbar);
            }else {
                toolbar.setNavigationIcon(R.mipmap.setting);
                setSupportActionBar(toolbar);
            }
            onRefresh();
        } else if (resultCode == 1003 && requestCode == REQUESECODE) {
            //onRefresh();
            //finish();
            //gt(MainActivity.class);
        }
    }

    @Override
    public void onRefresh() {
        presenter.getNewsList(Contants.REFREASH, "998", tags, "1", Contants.pageSize, SharedPrefrecesUtils.getStrFromSharedPrefrences("lang", getContext()));
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }

    @Override
    public void onLoadMore() {
        pageId++;
        if (pageId <= news.getTotpage()) {
            presenter.getNewsList(Contants.LOADMORE, "998", tags, String.valueOf(pageId), Contants.pageSize, SharedPrefrecesUtils.getStrFromSharedPrefrences("lang", getContext()));
        } else {
            getAdapter().stopMore();
            pageId = 1;
        }
    }

    @Override
    public void refreash(News news, List<News.ContentBean> modle) {
        this.newses = modle;
        this.news = news;
        getAdapter().clear();
        getAdapter().addAll(newses);
    }

    @Override
    public void loadMore(News news, List<News.ContentBean> modle) {
        newses.addAll(modle);
        this.news = news;
        if (newses.size() == 0) {
            getAdapter().stopMore();
        }
        getAdapter().clear();
        getAdapter().addAll(newses);
    }

    public void logoBack(View v){
        if(!tags.equals(Contants.DEFAULT)){
            tags = Contants.DEFAULT;
            toolbar.setNavigationIcon(R.mipmap.setting);
            setSupportActionBar(toolbar);
            onRefresh();
        }
    }

}
