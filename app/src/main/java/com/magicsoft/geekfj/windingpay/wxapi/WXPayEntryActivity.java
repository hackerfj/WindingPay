package com.magicsoft.geekfj.windingpay.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.magicsoft.geekfj.windingfjpay.IPayBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
    private IWXAPI api;

	public IPayBean.OnResultListener listener;

	public void setOnResultListener(IPayBean.OnResultListener listener) {
		this.listener = listener;
	}

	public interface OnResultListener {

		void onPaySuccess();

		void onPayFail();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this,"wx18eaff444811186d");//填写微信的APPKey
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Log.i("SLL",""+resp.errCode);
			if(resp.errCode == 0){
				listener.onPaySuccess();
				Toast.makeText(this, "支付成功!", Toast.LENGTH_SHORT).show();
			}else if(resp.errCode == -2){
				listener.onPayFail();
				Toast.makeText(this, "支付取消！", Toast.LENGTH_SHORT).show();
			}
			finish();
		}
	}
}