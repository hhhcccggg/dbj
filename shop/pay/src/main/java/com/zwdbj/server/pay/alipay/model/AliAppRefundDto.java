package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("ali支付申请退款后返还响应")
public class AliAppRefundDto implements Serializable {
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
    @ApiModelProperty("用户的登录id")
    @JSONField(name = "buyer_logon_id")
    private String buyerLogonId;
    @ApiModelProperty("本次退款是否发生了资金变化:Y,N")
    @JSONField(name = "fund_change")
    private String fundChange;
    @ApiModelProperty("退款总金额")
    @JSONField(name = "refund_fee")
    private String refundFee;
    @ApiModelProperty("退款支付时间")
    @JSONField(name = "gmt_refund_pay")
    private Date gmtRefundPay;
    @ApiModelProperty("买家在支付宝的用户id")
    @JSONField(name = "buyer_user_id")
    private String buyerUserId;


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

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getFundChange() {
        return fundChange;
    }

    public void setFundChange(String fundChange) {
        this.fundChange = fundChange;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public Date getGmtRefundPay() {
        return gmtRefundPay;
    }

    public void setGmtRefundPay(Date gmtRefundPay) {
        this.gmtRefundPay = gmtRefundPay;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }
}
