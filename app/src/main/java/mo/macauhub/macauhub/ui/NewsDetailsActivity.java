package mo.macauhub.macauhub.ui;

import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefUtils;
import cn.zhiao.baselib.webViewUtils.ProgressWebView;
import mo.macauhub.macauhub.R;
import mo.macauhub.macauhub.bean.Contants;
import mo.macauhub.macauhub.bean.News;

/**
 * Created by ymn on 2017/4/12.
 */
public class NewsDetailsActivity extends BaseActivity {
    @Bind(R.id.webView)
    ProgressWebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private News.ContentBean news ;
    private String detailUrl = "http://www.macauhub.com.mo";

    @Override
    public void initView() {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
        toolbar.setNavigationIcon(R.mipmap.arrow);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {
        news = (News.ContentBean) getIntent().getExtras().getSerializable("aid");
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        if(news!=null)
            detailUrl = Contants.DETAILS+"aid="+news.getAID()+"&lang="+ SharedPrefUtils.getStrFromSharedPrefrences("lang",getContext())+"&imei="+szImei;
        webView.loadUrl(detailUrl);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_details;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.clearHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                showShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(news.getAtitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(news.getAdesc());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(news.getThemeImg());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(detailUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }
    public void logoBack(View v){
        finish();
    }

    @Override
    public Object newP() {
        return null;
    }
}
