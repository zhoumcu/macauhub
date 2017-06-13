package com.macauhub.macauhub;

import android.content.Context;
import android.content.Intent;

import com.macauhub.macauhub.utils.TypefaceUtil;

import cn.sharesdk.framework.ShareSDK;
import cn.zhiao.baselib.app.BaseApplication;

/**
 * author：Administrator on 2017/4/14 11:07
 * company: xxxx
 * email：1032324589@qq.com
 */

public class App extends BaseApplication implements Thread.UncaughtExceptionHandler{

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        TypefaceUtil.replaceSystemDefaultFont(this,"fonts/Hind-Medium.ttf");
        ShareSDK.initSDK(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("uncaughtException");
                System.exit(0);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).start();
    }
}
