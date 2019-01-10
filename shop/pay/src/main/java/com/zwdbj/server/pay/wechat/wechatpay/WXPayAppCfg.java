package com.zwdbj.server.pay.wechat.wechatpay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信支付模块配置
 */
@Component
public class WXPayAppCfg {
    @Value("${app.pay.wechat.isSandBox}")
    private boolean isSandBox;
    @Value("${app.pay.wechat.payResultCallbackUrl}")
    private String payResultCallbackUrl;
    @Value("${app.pay.wechat.orderPayResultCallbackUrl}")
    private String orderPayResultCallbackUrl;
    @Value("${app.pay.wechat.certPath}")
    private String certPath;
    @Value("${app.pay.wechat.test}")
    private boolean isTest;

    public String getOrderPayResultCallbackUrl() {
        return orderPayResultCallbackUrl;
    }

    public void setOrderPayResultCallbackUrl(String orderPayResultCallbackUrl) {
        this.orderPayResultCallbackUrl = orderPayResultCallbackUrl;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public boolean isSandBox() {
        return isSandBox;
    }

    public void setSandBox(boolean sandBox) {
        isSandBox = sandBox;
    }

    public String getPayResultCallbackUrl() {
        return payResultCallbackUrl;
    }

    public void setPayResultCallbackUrl(String payResultCallbackUrl) {
        this.payResultCallbackUrl = payResultCallbackUrl;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(boolean isTest) {
        this.isTest = isTest;
    }
}
