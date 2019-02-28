package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("微信支付申请退款")
public class WXRefundOrderInput {

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String transactionId;
    /**
     * 商户退款单号
     */
    @ApiModelProperty("商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔")
    private String outRefundNo;

    /**
     * WECHAT:微信订单号MCH:商户订单号
     */
    @ApiModelProperty("WECHAT:微信订单号MCH:商户订单号")
    public  String type;
    @ApiModelProperty("订单金额")
    private int totalFee;
    @ApiModelProperty("退款金额")
    private int refundFee;
    @ApiModelProperty("退款原因")
    public  String refundDesc;
    @ApiModelProperty("支付回调地址")
    private String notifyUrl;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

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

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(int refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }
}
