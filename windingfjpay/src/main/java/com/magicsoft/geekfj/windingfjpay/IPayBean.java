package com.magicsoft.geekfj.windingfjpay;

/**
 * @author 沈小建 on 2016/12/5 0005.
 */

public class IPayBean {

    public OnResultListener listener;

    public void setOnResultListener(OnResultListener listener) {
        this.listener = listener;
    }

    public interface OnResultListener {

        void onPaySuccess();

        void onPayFail();
    }
}
