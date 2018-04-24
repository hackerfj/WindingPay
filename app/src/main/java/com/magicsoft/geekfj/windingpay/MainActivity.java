package com.magicsoft.geekfj.windingpay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.magicsoft.geekfj.windingfjpay.IPayBean;
import com.magicsoft.geekfj.windingfjpay.PayFactory;


public class MainActivity extends AppCompatActivity {
    private static String WXKEY = "wx18eaff444811186d";
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
        WechatPayData = "{\"appid\":\"wx18eaff444811186d\",\"partnerid\":\"1488654852\",\"prepayid\":\"wx24092447509486e511cff3d51164964171\",\"packageName\":\"Sign=WXPay\",\"noncestr\":\"Rdv0Q8FGA76n2G9b\",\"timestamp\":1524533087,\"sign\":\"3E4FA872CEB9A5BB2D75BAC388FD7E8F\"}";

        AliPayData = "alipay_sdk=alipay-sdk-php-20161101&app_id=2017090108499528&biz_content=%7B%22body%22%3A%22%E6%B5%8B%E8%AF%95%E6%B4%BB%E5%8A%A8%22%2C%22subject%22%3A+%22%E6%B5%8B%E8%AF%95%E6%B4%BB%E5%8A%A8%22%2C%22out_trade_no%22%3A+%2220180424090807f4216ca694f411%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%2215%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.hupovc.com.cn%2FAliPAY%2FAsynchronousNotification&sign_type=RSA2&timestamp=2018-04-24+09%3A08%3A07&version=1.0&sign=Z7VtVMGPdTqfyiJjZVoxBeotjJ%2Bue5Swy6A11KkhYAJvwjjezwlEYZxKZnasGQmirfqxSjgiKTzvkMUTYuZm8z%2FsIo67L1E1Ce30WwWGkK1kK7VPV5qzHXQnMyfF3Dr%2Bw5JFZ9GRQ%2FBDky8rpGQbGNYrgYV7cNGSNETnMu2lpOlNKkCijZcc3j2sb%2FNn9kAwX2bGL0i6Q9bdI%2FcjU4T%2BDNJfSdRALqNnU7z9GTYiBJ9PhtmVsB%2BpBsZwoUikpb%2BoMpoCVnU0YNawC3JF41Jd7DMaGPhaY4DTe8ABa11EYH4LHNdqfvSjd1EaYE3AVOARb425%2Bs66TpnQTtoj3i1vgg%3D%3D";
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
