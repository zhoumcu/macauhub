package com.zhiao.develop.macaumagazine.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhiao.develop.macaumagazine.App;
import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.bean.Contants;
import com.zhiao.develop.macaumagazine.ui.NewsDetailsActivity;
import com.zhiao.develop.macaumagazine.ui.TextActivity;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;

/**
 * Created by ymn on 2017/4/12.
 */
public class SettingFragment extends BaseFragment {
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
    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void jumpIn(AppCompatActivity at) {
        FragmentManager fragmentmanager = at.getSupportFragmentManager();
        SettingFragment fragment = SettingFragment.newInstance();
        fragmentmanager
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out)
                .replace(R.id.content_container, fragment, MenuFragment.MY_TAG)
                .commitAllowingStateLoss();
    }
    @Override
    public void initView() {
        if(SharedPrefrecesUtils.getStrFromSharedPrefrences("lang",getContext()).equals("zh")){
            rbZh.setChecked(true);
        }else if(SharedPrefrecesUtils.getStrFromSharedPrefrences("lang",getContext()).equals("en")){
            rbEn.setChecked(true);
        }else {
            rbLg.setChecked(true);
        }
        String ver = getResources().getString(R.string.version);
        version.setText(String.format(ver, BaseApplication.getVersion()));
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.aty_setting;
    }


    @OnClick({R.id.rb_zh, R.id.rb_en, R.id.rb_lg, R.id.about, R.id.used, R.id.disclaimer, R.id.email, R.id.phone, R.id.fax, R.id.address, R.id.homesite, R.id.version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_zh:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang","zh",getContext());
                getBaseActivity().switchLanguage(Locale.CHINA);
                getBaseActivity().replaceFragment(R.id.content_container,new SettingFragment());
                break;
            case R.id.rb_en:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang","en",getContext());
                getBaseActivity().switchLanguage(Locale.ENGLISH);
                getBaseActivity().replaceFragment(R.id.content_container,new SettingFragment());
                break;
            case R.id.rb_lg:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang","pt",getContext());
                getBaseActivity().replaceFragment(R.id.content_container,new SettingFragment());
                getBaseActivity().switchLanguage(App.locale);
                break;
            case R.id.about:
                Bundle bundle = new Bundle();
                bundle.putString(Contants.TEXT, getResources().getString(R.string.ContentTypeAbout));
                gt(bundle, TextActivity.class);
                break;
            case R.id.used:
                Bundle bundle1 = new Bundle();
                bundle1.putString(Contants.TEXT, getResources().getString(R.string.ContentTypeRules));
                gt(bundle1, TextActivity.class);
                break;
            case R.id.disclaimer:
                Bundle bundle2 = new Bundle();
                bundle2.putString(Contants.TEXT, getResources().getString(R.string.ContentTypeDisclaimer));
                gt(bundle2, TextActivity.class);
                break;
            case R.id.email:
                break;
            case R.id.phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String[] str = phone.getText().toString().split("\\:");
                Uri data = Uri.parse("tel:" + str[1]);
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.fax:
                break;
            case R.id.address:
                break;
            case R.id.homesite:
                Bundle bundle3 = new Bundle();
                bundle3.putString("aid", "0");
                gt(bundle3, NewsDetailsActivity.class);
                break;
            case R.id.version:
                break;
        }
    }
}
