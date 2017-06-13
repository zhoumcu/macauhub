package mo.macauhub.macauhub;

import android.content.Context;
import android.content.Intent;

import cn.sharesdk.framework.ShareSDK;
import cn.zhiao.baselib.app.BaseApplication;
import mo.macauhub.macauhub.utils.TypefaceUtil;

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
        //设置Thread Exception Handler
        Thread.setDefaultUncaughtExceptionHandler(this);
        TypefaceUtil.replaceSystemDefaultFont(this,"fonts/Hind-Medium.ttf");
        ShareSDK.initSDK(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
//                PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//                AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//                mgr.set(AlarmManager.RTC, System.currentTimeMillis()+10, restartIntent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }.start();
    }
}
