# WindingPay
风鼎科技支付插件
1.里面集成了支付宝支付和微信支付
一分钟集成集成支付不是梦
现在仅支持支付宝和微信
之后将集成银联等支付方式


将其添加到存储库末尾的根build.gradle中：
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

第2步.添加依赖关系
dependencies {
        compile 'com.github.luckyfj:WindingPay:v1.0.0'
}

#支付宝支付 使用方法
-----------------------------
PayFactory.createPay(PayFactory.ALPAY,MainActivity.this,“支付宝订单KEY”).setOnResultListener(new IPayBean.OnResultListener() {
    @Override
    public void onPaySuccess() {
        //TODO 支付宝支付支付成功回调
    }
    @Override
    public void onPayFail() {
        //TODO 支付宝支付支付失败回调
    }
});
----------------------------
#微信支付 使用方式
----------------------------
//微信支付
PayFactory.createPay(PayFactory.WXPAY, MainActivity.this,”微信订单KEY“).setOnResultListener(new IPayBean.OnResultListener() {
    @Override
    public void onPaySuccess() {
        //TODO 微信支付支付成功回调
    }

    @Override
    public void onPayFail() {
         //TODO 微信支付支付失败回调
    }
}));
