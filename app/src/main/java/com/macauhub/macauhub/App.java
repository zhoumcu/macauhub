package com.macauhub.macauhub;

import android.content.Intent;

import cn.sharesdk.framework.ShareSDK;
import cn.zhiao.baselib.app.BaseApplication;
import com.macauhub.macauhub.utils.TypefaceUtil;

/**
 * author：Administrator on 2017/4/14 11:07
 * company: xxxx
 * email：1032324589@qq.com
 */

public class App extends BaseApplication implements Thread.UncaughtExceptionHandler{

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.replaceSystemDefaultFont(this,"fonts/Hind-Medium.ttf");
        ShareSDK.initSDK(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        System.out.println("uncaughtException");
        System.exit(0);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
