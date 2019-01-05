package com.zwdbj.server.pay.alipay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliPayConfig {
    @Value("${app.pay.ali.payResultCallbackUrl}")
    private String payResultCallbackUrl;

    public String getPayResultCallbackUrl() {
        return payResultCallbackUrl;
    }

    public void setPayResultCallbackUrl(String payResultCallbackUrl) {
        this.payResultCallbackUrl = payResultCallbackUrl;
    }
}
