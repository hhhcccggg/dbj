package com.zwdbj.server.pay.alipay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliPayConfig {
    @Value("${app.pay.ali.payResultCallbackUrl}")
    private String payResultCallbackUrl;
    @Value("${app.pay.ali.orderPayResultCallbackUrl}")
    private String orderPayResultCallbackUrl;
    @Value("${app.pay.ali.orderPayResultCallbackUrl}")
    private String orderRefundResultCallbackUrl;

    public String getOrderRefundResultCallbackUrl() {
        return orderRefundResultCallbackUrl;
    }

    public void setOrderRefundResultCallbackUrl(String orderRefundResultCallbackUrl) {
        this.orderRefundResultCallbackUrl = orderRefundResultCallbackUrl;
    }

    public String getOrderPayResultCallbackUrl() {
        return orderPayResultCallbackUrl;
    }

    public void setOrderPayResultCallbackUrl(String orderPayResultCallbackUrl) {
        this.orderPayResultCallbackUrl = orderPayResultCallbackUrl;
    }

    public String getPayResultCallbackUrl() {
        return payResultCallbackUrl;
    }

    public void setPayResultCallbackUrl(String payResultCallbackUrl) {
        this.payResultCallbackUrl = payResultCallbackUrl;
    }
}
