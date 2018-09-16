package com.magicsoft.geekfj.windingpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.magicsoft.geekfj.windingfjpay.IPayBean;
import com.magicsoft.geekfj.windingfjpay.PayFactory;


public class MainActivity extends AppCompatActivity {

    private static String WXKEY = "wx18eaff444811186d";
    private Button AliPay;
    private Button WeChatPay;
    private Button btnRvTest;
    private String WechatPayData;
    private String AliPayData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }
    private void initEvent(){
        WechatPayData = "{\"appid\":\"wxb4ba3c02aa476ea1\",\"partnerid\":\"1900006771\",\"package\":\"Sign=WXPay\",\"noncestr\":\"646f0800ea6bb2a7f3d6db9a989b16c7\",\"timestamp\":1524564048,\"prepayid\":\"wx2418004833063002f93e9c771560504616\",\"sign\":\"E5C4618DF50297C301E7AE0A765A3141\"}";

        AliPayData = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018011201809112&biz_content=%7B%22body%22%3A%22%E8%AE%A2%E5%8D%95Sun+Sep+16+18%3A28%3A08+CST+2018%22%2C%22out_trade_no%22%3A%22201809160628071612%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%AE%A2%E5%8D%95Sun+Sep+16+18%3A28%3A08+CST+2018%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.sigus.cn%3A8081%2Fsigus%2Fapi%2Fopen%2Fpay%2Fali%2Fnotify&sign=Y0gb9k7aFS0j4chIi1p4f%2BkQXM2aq2HPut%2F08S%2BESxVNEV7qCzYeG3%2F3QBjSz%2BymvuBFqWllfgQCakQpQTBnnhLY3Ekc3UElx%2BjrvinZlE4QWmrpqx0LgQUYAENRnN8OFvHvhXdZ4pmsHIdGRPwdGOHN0n0XJ7l90MBr%2Bh%2FElJgJFON6Z5MC7NM2GRWvhNBowAE6ok3wGDEPa33owx7drfa6gIWwaYccgK6Nvogydon6dDmtkbAqivrZkUukIxq5RxsB6LTxsSnrO4vE5peg4qucWE8XJSpsR%2Bt1xC2nUbJkrj431zOutSciDZzbN%2BPxsp7O00RQIHxgqom40%2FGyaQ%3D%3D&sign_type=RSA2&timestamp=2018-09-16+18%3A28%3A08&version=1.0";
        AliPay = findViewById(R.id.btn_pay_ali);
        WeChatPay = findViewById(R.id.btn_pay_wechat);
        btnRvTest = findViewById(R.id.btn_rv_test);

        //支付宝支付
        AliPay.setOnClickListener(v -> PayFactory.createPay(PayFactory.ALPAY,this,AliPayData)
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
        //跳转到封装的RecyclerView
        btnRvTest.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,RvDemoActivity.class)));
    }
}
