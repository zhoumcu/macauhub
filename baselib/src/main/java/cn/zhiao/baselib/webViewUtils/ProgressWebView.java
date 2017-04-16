package cn.zhiao.baselib.webViewUtils;

/**
 * author：Administrator on 2017/2/22 14:56
 * company: xxxx
 * email：1032324589@qq.com
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.zhiao.baselib.R;

/**
 * @Description:带进度条的WebView
 * @author http://blog.csdn.net/finddreams
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {
    public static final String APP_CACAHE_DIRNAME = "/webcache";
    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                10, 0, 0));

        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        //是否可以缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        getSettings().setDatabaseEnabled(true);
        String cacheDirPath = context.getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
        //设置数据库缓存路径
        getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        getSettings().setAppCacheEnabled(true);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}

