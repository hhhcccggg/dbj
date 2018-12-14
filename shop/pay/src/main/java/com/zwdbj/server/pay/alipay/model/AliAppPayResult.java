package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * APP支付结果
 */
@ApiModel("APP支付结果")
public class AliAppPayResult implements Serializable {
    /**
     * 商户订单号
     */
    @ApiModelProperty("商户订单号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * 该交易在支付宝系统中的交易流水号。
     */
    @ApiModelProperty("该交易在支付宝系统中的交易流水号。")
    @JSONField(name = "trade_no")
    private String tradeNo;
    /**
     * 该笔订单的资金总额，元
     */
    @ApiModelProperty("该笔订单的资金总额，元")
    @JSONField(name = "total_amount")
    private String totalAmount;
    /**
     * 收款支付宝账号对应的支付宝唯一用户号。
     * 以2088开头的纯16位数字
     */
    @ApiModelProperty("收款支付宝账号对应的支付宝唯一用户号。 \n" +
            "以2088开头的纯16位数字")
    @JSONField(name = "seller_id")
    private String sellerId;
    /**
     * 移动端需要的订单信息字符串
     */
    @ApiModelProperty("移动端需要的订单信息字符串")
    private String orderString;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }
}
