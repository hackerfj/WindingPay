package com.magicsoft.geekfj.windingfjpay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;


import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Created 风鼎科技
 */
public class ALPay extends IPayBean {

    public static final int SDK_PAY_FLAG = 1;

    private static final String SUCCESS = "9000";
    private static final String WAIT_SURE = "8000";

    private String hint = "需要配置PARTNER | RSA_PRIVATE| SELLER";
    private String paySuccess = "支付成功";
    private String payFail = "支付失败";
    private String paySure = "支付结果确认中";


    private Context mContext;
    private String content;


    public ALPay(Context mContext, String content) {
        this.mContext = mContext;
        this.content = content.replace("\"","");
        alipay();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != SDK_PAY_FLAG) {
                return;
            }
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            switch (resultStatus) {
                case SUCCESS:
                    toastMessage(paySuccess);
                    if (null != listener) {
                        listener.onPaySuccess();
                    }
                    break;
                case WAIT_SURE:
                    toastMessage(payFail);
                    break;
                default:
                    toastMessage(paySure);
                    if (null != listener) {
                        listener.onPayFail();
                    }
                    break;
            }
        }
    };
    private void toastMessage(String message) {
        Toast.makeText(mContext, message,
                Toast.LENGTH_SHORT).show();
    }
    /**
     * 判断密匙或公钥.
     */
    private void alipay() {
        if (TextUtils.isEmpty(content)) {
            toastMessage(hint);
            return;
        }
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(content, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
