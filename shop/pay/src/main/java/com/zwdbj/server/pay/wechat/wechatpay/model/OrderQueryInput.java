package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单查询")
public class OrderQueryInput {

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String transactionId;

    /**
     * WECHAT:微信订单号MCH:商户订单号
     */
    @ApiModelProperty("WECHAT:微信订单号MCH:商户订单号")
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
