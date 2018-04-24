package com.magicsoft.geekfj.windingfjpay;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付
 * Created 风鼎科技
 */
public class WXPay extends IPayBean {

    private IWXAPI api;
    private Context mContext;
    private WxPayBean payModel;

    public WXPay(Context context, WxPayBean payModel) {
        mContext = context;
        this.payModel = payModel;
        wxPay();
    }

    private void wxPay() {
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(payModel.getAppid());
        PayReq req = new PayReq();
        req.appId = payModel.getAppid();
        req.partnerId = payModel.getPartnerid();
        req.prepayId = payModel.getPrepayid();
        req.packageValue = payModel.getPackageX();
        req.nonceStr = payModel.getNoncestr();
        req.timeStamp = String.valueOf(payModel.getTimestamp());
        req.sign = payModel.getSign();

        api.sendReq(req);
    }
}
