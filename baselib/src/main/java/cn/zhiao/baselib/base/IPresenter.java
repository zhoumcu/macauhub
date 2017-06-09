package cn.zhiao.baselib.base;

/**
 * author：Administrator on 2017/6/9 16:03
 * company: xxxx
 * email：1032324589@qq.com
 */

public interface IPresenter<V> {
    void attachV(V view);

    void detachV();
}
