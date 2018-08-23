# WindingPay
遨易科技支付插件
1.里面集成了支付宝支付和微信支付
一分钟集成集成支付不是梦
现在仅支持支付宝和微信
之后将集成银联等支付方式


将其添加到存储库末尾的根build.gradle中：<br/>
allprojects {<br/>
&nbsp;&nbsp;&nbsp;  repositories {<br/>
&nbsp;&nbsp;&nbsp;    ...<br/>
&nbsp;&nbsp;&nbsp;    maven { url 'https://jitpack.io' }<br/>
&nbsp;&nbsp;  }<br/>
}<br/>

第2步.添加依赖关系<br/>
dependencies {<br/>
&nbsp;&nbsp;        compile 'com.github.luckyfj:WindingPay:v1.0.0'<br/>
}<br/>
<br/>
#支付宝支付 使用方法<br/>
-----------------------------<br/>
PayFactory.createPay(PayFactory.ALPAY,MainActivity.this,“支付宝订单KEY”)<br/>
.setOnResultListener(new IPayBean.OnResultListener() {<br/>
    @Override<br/>
    public void onPaySuccess() {<br/>
 &nbsp;&nbsp;       //TODO 支付宝支付支付成功回调<br/>
    }<br/>
    @Override<br/>
    public void onPayFail() {<br/>
&nbsp;&nbsp;        //TODO 支付宝支付支付失败回调<br/>
    }<br/>
});<br/>
----------------------------<br/>
#微信支付 使用方式<br/>
----------------------------<br/>
//微信支付<br/>
PayFactory.createPay(PayFactory.WXPAY, MainActivity.this,”微信订单KEY“)<br/>
  .setOnResultListener(new IPayBean.OnResultListener() {<br/>
    @Override<br/>
    public void onPaySuccess() {<br/>
&nbsp;&nbsp;        //TODO 微信支付支付成功回调<br/>
    }<br/>
<br/>
    @Override<br/>
    public void onPayFail() {<br/>
&nbsp;&nbsp;         //TODO 微信支付支付失败回调<br/>
    }<br/>
}));<br/>


微信支付回调配置

新建一个wxapi文件夹
然后新建类

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this,"wx18eaff444811186d");//填写微信APPKey
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
				Toast.makeText(this, "支付成功!", Toast.LENGTH_SHORT).show();
			}else if(resp.errCode == -2){
				Toast.makeText(this, "支付取消！", Toast.LENGTH_SHORT).show();
			}
			finish();
		}
	}
}


