package cn.zhiao.baselib.net;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import cn.zhiao.baselib.utils.log.XLog;

public class TransactionAsyncHttpStringHandler extends TextHttpResponseHandler {
    StringTransactionListener mTransactionListener;

    /**
     * 自定义http响应处理器
     *
     * @param mTransactionListener
     */
    public TransactionAsyncHttpStringHandler(StringTransactionListener mTransactionListener) {
        this.mTransactionListener = mTransactionListener;
        setCharset(AsyncHttpNetCenter.CONTENT_ENCODING);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        mTransactionListener.onFailure(ResponseCode.ERROR_NETWORK);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        XLog.e("*** HTTP-Response,data：" + responseString);

        mTransactionListener.onSuccess(responseString);
    }
}
