package com.magicsoft.geekfj.windingfjpay;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;


/**
 * @author 风鼎科技
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
//                wxPayBean.setPackageX("Sign=WXPay");
                return new WXPay(context, wxPayBean);
        }
        return null;
    }
}
