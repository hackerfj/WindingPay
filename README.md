### 上班墨鱼兄-支付插件

#### 集成了支付宝支付和微信支付一分钟集成集成支付不是梦现在仅支持支付宝和微信之后将集成银联等支付方式



第一步 将其添加到存储库末尾的根的build.gradle中：


    allprojects {
    		repositories {
    			...
   			 maven { url 'https://jitpack.io' }
    		}
	   }
第2步.添加依赖关系:

		dependencies {
		   compile 'com.github.luckyfj:WindingPay:v1.0.0'
		}

#### 支付宝支付 使用方法
```javascript
//支付宝支付
			AliPay.setOnClickListener(v -> PayFactory.createPay(PayFactory.ALPAY,this,“支付宝订单码”)
				.setOnResultListener(new IPayBean.OnResultListener() {
					@Override
					public void onPaySuccess() {
						Toast.makeText(MainActivity.this, "支付宝支付成功！", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onPayFail() {
						Toast.makeText(MainActivity.this, "支付宝支付失败！", Toast.LENGTH_SHORT).show();
					}
			}));
```

#### 微信支付 使用方法
```javascript
//微信支付
			WeChatPay.setOnClickListener(v -> PayFactory.createPay(PayFactory.WXPAY, MainActivity.this,WechatPayData)
				.setOnResultListener(new IPayBean.OnResultListener() {
					@Override
					public void onPaySuccess() {
						Toast.makeText(MainActivity.this, "微信支付成功！", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onPayFail() {
						Toast.makeText(MainActivity.this, "微信支付失败！", Toast.LENGTH_SHORT).show();
					}
			}));
```

### 微信支付回调配置
1.新建一个wxapi文件夹 然后新建类
```javascript
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
```
