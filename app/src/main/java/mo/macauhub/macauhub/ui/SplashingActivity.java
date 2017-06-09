package mo.macauhub.macauhub.ui;

import java.util.Timer;
import java.util.TimerTask;

import cn.zhiao.baselib.base.BaseActivity;
import mo.macauhub.macauhub.MainActivity;
import mo.macauhub.macauhub.R;

/**
 * Created by ymn on 2017/4/12.
 */
public class SplashingActivity extends BaseActivity {

    @Override
    public void initView() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gt(MainActivity.class);
                finish();
            }
        },2000);
        switchLanguage();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_splash;
    }

    @Override
    public Object newP() {
        return null;
    }
}
