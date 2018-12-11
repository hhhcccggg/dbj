package com.zwdbj.server.pay.wechat.wechatpay.model;

/**
 * 微信支付结果通知
 */
public class PayNotifyResult {
    private String responseWeChatXML;
    private OrderPayResultDto payResultDto;

    public String getResponseWeChatXML() {
        return responseWeChatXML;
    }

    public void setResponseWeChatXML(String responseWeChatXML) {
        this.responseWeChatXML = responseWeChatXML;
    }

    public OrderPayResultDto getPayResultDto() {
        return payResultDto;
    }

    public void setPayResultDto(OrderPayResultDto payResultDto) {
        this.payResultDto = payResultDto;
    }
}
