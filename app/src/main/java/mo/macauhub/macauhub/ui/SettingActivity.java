package mo.macauhub.macauhub.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import mo.macauhub.macauhub.App;
import mo.macauhub.macauhub.R;
import mo.macauhub.macauhub.bean.Contants;

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
    @Bind(R.id.view)
    View view;
    @Bind(R.id.about)
    TextView about;
    @Bind(R.id.used)
    TextView used;
    @Bind(R.id.disclaimer)
    TextView disclaimer;
    @Bind(R.id.contantme)
    TextView contantme;
    @Bind(R.id.softversion)
    TextView softversion;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void initView(){
        toolbar.setNavigationIcon(R.mipmap.arrow);
        setSupportActionBar(toolbar);

        if (SharedPrefrecesUtils.getStrFromSharedPrefrences("lang", getContext()).equals("zh")) {
            rbZh.setChecked(true);
        } else if (SharedPrefrecesUtils.getStrFromSharedPrefrences("lang", getContext()).equals("en")) {
            rbEn.setChecked(true);
        } else {
            rbLg.setChecked(true);
        }
        initLang();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_setting;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.info_close, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_close:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @OnClick({R.id.rb_zh, R.id.rb_en, R.id.rb_lg, R.id.about, R.id.used, R.id.disclaimer, R.id.email, R.id.phone, R.id.fax, R.id.address, R.id.homesite, R.id.version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_zh:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang", "zh", getContext());
                switchLanguage(Locale.CHINA);
                initLang();
                break;
            case R.id.rb_en:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang", "en", getContext());
                switchLanguage(Locale.ENGLISH);
                initLang();
                break;
            case R.id.rb_lg:
                SharedPrefrecesUtils.saveStrToSharedPrefrences("lang", "pt", getContext());
                switchLanguage(App.locale);
                initLang();
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
//                Intent intent1 =new Intent(Intent.ACTION_SENDTO);
//                String[] stre = email.getText().toString().split("\\:");
//                intent1.setData(Uri.parse(stre[1].trim()));
//                intent1.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
//                intent1.putExtra(Intent.EXTRA_TEXT, "这是内容");
//                startActivity(intent1);
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
                gt(bundle3, NewsDetailsActivity.class);
                break;
            case R.id.version:
                break;
        }
    }

    private void initLang() {
        //getAcitonbar().setTitle(R.string.setting);
//        getAcitonbar().setDisplayShowHomeEnabled(true);
//        getAcitonbar().setLogo(R.mipmap.alogo);
//        getAcitonbar().setDisplayUseLogoEnabled(true);
//        getAcitonbar().setDisplayShowTitleEnabled(false);
//        getAcitonbar().setDisplayHomeAsUpEnabled(true);
//        getAcitonbar().setHomeAsUpIndicator(R.mipmap.arrow);
        about.setText(getResources().getString(R.string.about));
        used.setText(getResources().getString(R.string.used));
        disclaimer.setText(getResources().getString(R.string.disclaimer));
        email.setText(getResources().getString(R.string.email));
        phone.setText(getResources().getString(R.string.phone));
        fax.setText(getResources().getString(R.string.fax));
        address.setText(getResources().getString(R.string.address));
        homesite.setText(getResources().getString(R.string.homesite));
        contantme.setText(getResources().getString(R.string.contactme));
        softversion.setText(getResources().getString(R.string.softversion));
        String ver = getResources().getString(R.string.version);
        version.setText(String.format(ver, BaseApplication.getVersion()));
    }

    public void logoBack(View v){
        finish();
    }
}
