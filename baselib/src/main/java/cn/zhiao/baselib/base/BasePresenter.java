package cn.zhiao.baselib.base;

import android.text.TextUtils;

/**
 * 公共Presenter,所有Presenter继承自此类
 * 因为Presenter层一般用于校验数据正确性,故该类用于封装常用的数据校验方法
 */
public abstract class BasePresenter<V extends IBaseView> implements IPresenter<V>{

    private V v;

    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {
        v = null;
    }

    protected V getV() {
        if (v == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v;
    }
    /**
     * 校验指定的字符串是否为空,如果为空则弹出指定内容的Toast
     *
     * @param verifData
     * @param view
     * @param showMessage
     * @return
     */
    public boolean isEmpty(String verifData, IBaseView view, String showMessage) {
        if (TextUtils.isEmpty(verifData)) {
            view.showToast(showMessage);
            return true;
        }
        return false;
    }
}
