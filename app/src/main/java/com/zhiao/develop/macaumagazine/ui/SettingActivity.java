package com.zhiao.develop.macaumagazine.ui;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhiao.develop.macaumagazine.App;
import com.zhiao.develop.macaumagazine.R;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;

/**
 * Created by ymn on 2017/4/12.
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.rb_zh)
    RadioButton rbZh;
    @Bind(R.id.rb_en)
    RadioButton rbEn;
    @Bind(R.id.rb_lg)
    RadioButton rbLg;
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.fax)
    TextView fax;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.homesite)
    TextView homesite;
    @Bind(R.id.version)
    TextView version;

    @Override
    public void initView() {
        if(SharedPrefrecesUtils.getStrFromSharedPrefrences("lang",getContext()).equals("zh")){
            rbZh.setChecked(true);
        }else if(SharedPrefrecesUtils.getStrFromSharedPrefrences("lang",getContext()).equals("en")){
            rbEn.setChecked(true);
        }else {
            rbLg.setChecked(true);
        }
        String emailStr = getResources().getString(R.string.email);
        String emailStr1 = String.format(emailStr,"xxx@qq.com");
        email.setText(emailStr1);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_setting;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rb_zh, R.id.rb_en, R.id.rb_lg, R.id.about, R.id.used, R.id.disclaimer, R.id.email, R.id.phone, R.id.fax, R.id.address, R.id.homesite, R.id.version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_zh:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang","zh",getContext());
                switchLanguage(Locale.CHINA,SettingActivity.class);
                break;
            case R.id.rb_en:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang","en",getContext());
                switchLanguage(Locale.ENGLISH,SettingActivity.class);
                break;
            case R.id.rb_lg:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang","pt",getContext());
                switchLanguage(App.locale,SettingActivity.class);
                break;
            case R.id.about:
                break;
            case R.id.used:
                break;
            case R.id.disclaimer:
                break;
            case R.id.email:
                break;
            case R.id.phone:
                break;
            case R.id.fax:
                break;
            case R.id.address:
                break;
            case R.id.homesite:
                break;
            case R.id.version:
                break;
        }
    }
}
