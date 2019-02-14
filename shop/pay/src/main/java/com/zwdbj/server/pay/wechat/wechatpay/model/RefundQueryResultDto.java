package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "微信查询退款时返回的结果")
public class RefundQueryResultDto {
    @ApiModelProperty(value = "微信退款单号")
    private String refundId;
    @ApiModelProperty(value = "商户退款单号")
    private String outRefundNo;
    @ApiModelProperty(value = "微信订单单号")
    private String transactionId;
    @ApiModelProperty(value = "商户订单单号")
    private String outTradeNo;
    @ApiModelProperty("订单金额")
    private int totalFee;
    @ApiModelProperty("申请退款金额")
    private int refundFee;
    @ApiModelProperty("退款金额")
    private int settlementRefundFee;
    @ApiModelProperty(value = "现金支付金额")
    private int cashFee;
    @ApiModelProperty(value = "退款笔数")
    private int refundCount;
    @ApiModelProperty(value = "退款状态:" +
            "SUCCESS—退款成功" +
            "REFUNDCLOSE—退款关闭" +
            "PROCESSING—退款处理中" +
            "CHANGE—退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败")
    private String refundStatus;
    @ApiModelProperty(value = "退款入账账户 取当前退款单的退款入账方 " +
            "1）退回银行卡： {银行名称}{卡类型}{卡尾号} " +
            "2）退回支付用户零钱: 支付用户零钱 " +
            "3）退还商户:商户基本账户 商户结算银行账户 " +
            "4）退回支付用户零钱通: 支付用户零钱通")
    private String refundRecvAccout;
    @ApiModelProperty("移动端可以根据此字段判定是否交易支付成功")
    private boolean refundOrNot;
    @ApiModelProperty(value = "退款成功时间")
    private String refundSuccessTime;
    @ApiModelProperty(value = "退款资金来源")
    private String refundAccount;
    @ApiModelProperty(value = "退款发起来源")
    private String reFundRequestSource;

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getReFundRequestSource() {
        return reFundRequestSource;
    }

    public void setReFundRequestSource(String reFundRequestSource) {
        this.reFundRequestSource = reFundRequestSource;
    }

    public int getSettlementRefundFee() {
        return settlementRefundFee;
    }

    public void setSettlementRefundFee(int settlementRefundFee) {
        this.settlementRefundFee = settlementRefundFee;
    }

    public void setRefundOrNot(boolean refundOrNot) {
        this.refundOrNot = refundOrNot;
    }

    public String getRefundSuccessTime() {
        return refundSuccessTime;
    }

    public void setRefundSuccessTime(String refundSuccessTime) {
        this.refundSuccessTime = refundSuccessTime;
    }

    public boolean isRefundOrNot() {
        refundOrNot = this.getRefundStatus().equals("SUCCESS");
        return refundOrNot;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
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

    public int getCashFee() {
        return cashFee;
    }

    public void setCashFee(int cashFee) {
        this.cashFee = cashFee;
    }

    public int getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(int refundCount) {
        this.refundCount = refundCount;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundRecvAccout() {
        return refundRecvAccout;
    }

    public void setRefundRecvAccout(String refundRecvAccout) {
        this.refundRecvAccout = refundRecvAccout;
    }
}
