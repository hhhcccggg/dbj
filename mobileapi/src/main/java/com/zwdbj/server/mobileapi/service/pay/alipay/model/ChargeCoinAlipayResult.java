package com.zwdbj.server.mobileapi.service.pay.alipay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("返回信息")
public class ChargeCoinAlipayResult implements Serializable {
    @ApiModelProperty("订单信息")
    private String body;
    @ApiModelProperty("订单主题")
    private String subject;
    @ApiModelProperty("商户订单号")
    private String outTradeNo;
    @ApiModelProperty("超时时间")
    private String timeoutExpress;
    @ApiModelProperty("订单金额，元")
    private float totalAmount;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
