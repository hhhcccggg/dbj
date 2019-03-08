package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("阿里支付退款查询的响应字段")
public class AliAppRefundQueryDto implements Serializable {

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
    @ApiModelProperty(value = "本笔退款对应的退款请求号")
    @JSONField(name = "out_request_no")
    private String outRequestNo;
    @ApiModelProperty(value = "发起退款时，传入的退款原因")
    @JSONField(name = "refund_reason")
    private String refundReason;

    @ApiModelProperty("该笔退款所对应的交易的订单金额")
    @JSONField(name = "total_amount")
    private String totalAmount;
    @ApiModelProperty("本次退款请求，对应的退款金额")
    @JSONField(name = "refund_amount")
    private String refundAmount;
    @ApiModelProperty("本次商户实际退回金额；默认不返回该信息，需与支付宝约定后配置返回；")
    @JSONField(name = "send_back_fee")
    private String sendBackFee;
    @ApiModelProperty("退款支付时间")
    @JSONField(name = "gmt_refund_pay")
    private Date gmtRefundPay;
    @ApiModelProperty("业务返回码:ACQ.TRADE_HAS_SUCCESS:退款成功,ACQ.SYSTEM_ERROR:系统错误,ACQ.INVALID_PARAMETER:参数无效,TRADE_NOT_EXIST:查询退款的交易不存在")
    @JSONField(name = "sub_code")
    private String subCode;
    @ApiModelProperty("")
    @JSONField(name = "code")
    private String code;
    @ApiModelProperty("")
    @JSONField(name = "msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

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

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getSendBackFee() {
        return sendBackFee;
    }

    public void setSendBackFee(String sendBackFee) {
        this.sendBackFee = sendBackFee;
    }

    public Date getGmtRefundPay() {
        return gmtRefundPay;
    }

    public void setGmtRefundPay(Date gmtRefundPay) {
        this.gmtRefundPay = gmtRefundPay;
    }
}
