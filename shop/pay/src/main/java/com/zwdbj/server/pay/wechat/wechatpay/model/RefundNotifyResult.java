package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "微信退款异步通知")
public class RefundNotifyResult {
    private String responseWeChatXML;

    private RefundQueryResultDto dto;


    public RefundQueryResultDto getDto() {
        return dto;
    }

    public void setDto(RefundQueryResultDto dto) {
        this.dto = dto;
    }

    public String getResponseWeChatXML() {
        return responseWeChatXML;
    }

    public void setResponseWeChatXML(String responseWeChatXML) {
        this.responseWeChatXML = responseWeChatXML;
    }


}
