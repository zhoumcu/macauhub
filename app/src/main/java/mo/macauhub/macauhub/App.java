package mo.macauhub.macauhub;

import cn.sharesdk.framework.ShareSDK;
import cn.zhiao.baselib.app.BaseApplication;
import mo.macauhub.macauhub.utils.TypefaceUtil;

/**
 * author：Administrator on 2017/4/14 11:07
 * company: xxxx
 * email：1032324589@qq.com
 */

public class App extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.replaceSystemDefaultFont(this,"fonts/Hind-Medium.ttf");
        ShareSDK.initSDK(this);
    }
}
