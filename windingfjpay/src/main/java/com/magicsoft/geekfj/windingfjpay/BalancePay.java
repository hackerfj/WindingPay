package com.magicsoft.geekfj.windingfjpay;

import android.os.Handler;
import android.os.Message;

/**
 * @author 沈小建 on 2016/12/5 0005.
 */

public class BalancePay extends IPayBean {
    private static final int DELAY = 300;

    public BalancePay() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (null != listener) {
                    listener.onPaySuccess();
                }
            }
        }.sendEmptyMessageDelayed(0, DELAY);
    }
}
