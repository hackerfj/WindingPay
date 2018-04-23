package com.magicsoft.geekfj.windingpay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

import com.magicsoft.geekfj.windingfjpay.IPayBean;
import com.magicsoft.geekfj.windingfjpay.PayFactory;


public class MainActivity extends AppCompatActivity {
    private Button AliPay;
    private Button WeChatPay;
    private String WechatPayData;
    private String AliPayData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }
    private void initEvent(){
        WechatPayData = "";
        AliPayData = "alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2017090108499528&amp;biz_content=%7B%22body%22%3A%222017%E4%B8%AD%E5%9B%BD%E4%BA%92%E8%81%94%E7%BD%91%2B%E6%96%B0%E5%95%86%E4%B8%9A%E5%B3%B0%E4%BC%9A%22%2C%22subject%22%3A+%222017%E4%B8%AD%E5%9B%BD%E4%BA%92%E8%81%94%E7%BD%91%2B%E6%96%B0%E5%95%86%E4%B8%9A%E5%B3%B0%E4%BC%9A%22%2C%22out_trade_no%22%3A+%22201709240935480fb79abe88882e4d63%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=http%3A%2F%2Fapi.hupovc.com.cn%2FAliPAY%2FAsynchronousNotification&amp;sign_type=RSA2&amp;timestamp=2017-09-24+09%3A35%3A48&amp;version=1.0&amp;sign=Z4KBZjPnMor71L3URjeyoaykcQaPouWBW5X4XY9LGk1Lkdmyu1rZSmQaJkklYDFwvfJZiYwPiIa8AL%2BusRepxgUemfstQAZyHCZ10UyX41fOGzVwf%2BjWeASl40IJ2xAOfIFh2njAxMzQzQHO6g4PNUTpRtA7W%2FBhn7DlOpJZkX4A8AEjX2d96gR1wMdO66qEU7XKN0Q6ckm1qqrqkNZL%2BE3DoPKwfqdeg5wnWSmG9%2B%2BqRfNz%2BpDQmUep%2B1h0PNYdLZxNTEeKSyxDOjzS4U%2BufUQYiLQ925vg3hwzp7gDS%2F%2BXbLOXIJ4Bf0nczIZiTf4wPLE%2FVzbPTBax3qscitcvUg%3D%3D";
        AliPay = findViewById(R.id.btn_pay_ali);
        WeChatPay = findViewById(R.id.btn_pay_wechat);

        //支付宝支付
        AliPay.setOnClickListener(v -> PayFactory.createPay(PayFactory.ALPAY,this,AliPayData).setOnResultListener(new IPayBean.OnResultListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(MainActivity.this, "支付宝支付成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayFail() {
                Toast.makeText(MainActivity.this, "支付宝支付失败！", Toast.LENGTH_SHORT).show();
            }
        }));

        //微信支付
        WeChatPay.setOnClickListener(v -> PayFactory.createPay(PayFactory.WXPAY, MainActivity.this,WechatPayData).setOnResultListener(new IPayBean.OnResultListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(MainActivity.this, "微信支付成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayFail() {
                Toast.makeText(MainActivity.this, "微信支付失败！", Toast.LENGTH_SHORT).show();
            }
        }));

    }
}
