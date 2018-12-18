package com.zwdbj.server.pay.alipay.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 查询结果
 */
@ApiModel("订单查询结果")
public class AliOrderQueryResult implements Serializable {

    /**
     * 买家实付金额，单位为元，两位小数。该金额代表该笔交易买家实际支付的金额，不包含商户折扣等金额
     */
    @ApiModelProperty("买家实付金额，单位为元，两位小数。该金额代表该笔交易买家实际支付的金额，不包含商户折扣等金额")
    private String buyerPayAmount;

    /**
     * 商家订单号
     */
    @ApiModelProperty("商家订单号")
    private String outTradeNo;

    /**
     * 交易的订单金额，单位为元，两位小数。该参数的值为支付时传入的total_amount
     */
    @ApiModelProperty("交易的订单金额，单位为元，两位小数。该参数的值为支付时传入的total_amount")
    private String totalAmount;

    /**
     * 支付宝交易号
     */
    @ApiModelProperty("支付宝交易号")
    private String tradeNo;

    /**
     * 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
     */
    @ApiModelProperty("交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）")
    private String tradeStatus;
    @ApiModelProperty("是否已支付成功，移动端可以根据此字段直接判断是否已支付")
    private boolean isPay;

    public boolean isPay() {
        isPay = this.getTradeStatus().equals("TRADE_SUCCESS");
        return isPay;
    }

    public String getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(String buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
}
