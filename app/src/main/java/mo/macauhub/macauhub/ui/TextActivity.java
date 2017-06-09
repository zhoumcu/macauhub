package mo.macauhub.macauhub.ui;

import android.support.v7.app.ActionBar;
import android.widget.TextView;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseActivity;
import mo.macauhub.macauhub.R;
import mo.macauhub.macauhub.bean.Contants;
import mo.macauhub.macauhub.interfaces.presenter.NewsPresenterImpl;

/**
 * Created by ymn on 2017/4/12.
 */
public class TextActivity extends BaseActivity<NewsPresenterImpl> {

    @Bind(R.id.tv_text)
    TextView tvText;

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.alogo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
        tvText.setText( getIntent().getStringExtra(Contants.TEXT));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_text;
    }

    @Override
    public NewsPresenterImpl newP() {
        return new NewsPresenterImpl();
    }
}
