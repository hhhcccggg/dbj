package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "微信退款查询退款的输入")
public class RefundQueryInput implements Serializable {

    private static final long serialVersionUID = 6976532922723646795L;
    /**
     * 订单号
     */
    @ApiModelProperty("订单号或退单号")
    private String transactionId;

    /**
     * WECHAT:微信订单号 MCH:商户订单号,REWECHAT:微信退单号,REMCH:商户退款单号
     */
    @ApiModelProperty("WECHAT:微信订单号MCH:商户订单号,REWECHAT:微信退单号,REMCH:商户退款单号")
    public  String type;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
