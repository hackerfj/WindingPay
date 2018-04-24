package com.magicsoft.geekfj.windingfjpay;

/**
 * @author 风鼎科技
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
