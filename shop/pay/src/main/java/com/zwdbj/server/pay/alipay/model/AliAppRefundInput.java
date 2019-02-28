package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "支付宝申请退款的请求参数")
public class AliAppRefundInput implements Serializable {
    private static final long serialVersionUID = 5181281787350543757L;
    @ApiModelProperty(value = "订单支付时传入的商户订单号,不能和 trade_no同时为空。")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    @ApiModelProperty(value = "支付宝交易号，和商户订单号不能同时为空。")
    @JSONField(name = "trade_no")
    private String tradeNo;
    @ApiModelProperty(value = "标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传")
    @JSONField(name = "out_request_no")
    private String outRequestNo;
    @ApiModelProperty("退款金额，单位元")
    @JSONField(name = "refund_amount")
    private String refundAmount;
    @ApiModelProperty("退款的原因说明")
    @JSONField(name = "refund_reason")
    private String refundReason;

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
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


    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
}
