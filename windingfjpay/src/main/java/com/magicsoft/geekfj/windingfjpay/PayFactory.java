package com.magicsoft.geekfj.windingfjpay;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;


/**
 * @author 沈小建 on 2016/12/5 0005.
 */

public class PayFactory {

    public static final int BALANCE = 1;
    public static final int ALPAY = 2;
    public static final int WXPAY = 3;

    public static IPayBean createPay(int type, Context context, String content) {
        switch (type) {
            case BALANCE:
                return new BalancePay();
            case ALPAY:
                return new ALPay(context, content);
            case WXPAY:
                WxPayBean wxPayBean = JSON.parseObject(content, WxPayBean.class);
                Log.i("SLL",""+wxPayBean.getAppid());
                Log.i("SLL",""+wxPayBean.getPartnerid());
                Log.i("SLL",""+wxPayBean.getPrepayid());
                Log.i("SLL",""+wxPayBean.getPackageX());
                Log.i("SLL",""+wxPayBean.getNoncestr());
                Log.i("SLL",""+wxPayBean.getTimestamp());
                Log.i("SLL",""+wxPayBean.getSign());
                return new WXPay(context, wxPayBean);
        }
        return null;
    }
}
